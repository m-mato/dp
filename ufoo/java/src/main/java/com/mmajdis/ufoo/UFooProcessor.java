package com.mmajdis.ufoo;

import com.mmajdis.ufoo.analyzer.FootprintSimilarityService;
import com.mmajdis.ufoo.analyzer.Serializer;
import com.mmajdis.ufoo.endpoint.collector.http.HTTPFootprint;
import com.mmajdis.ufoo.endpoint.collector.tcp.PacketStream;
import com.mmajdis.ufoo.endpoint.collector.tcp.TCPFootprint;
import com.mmajdis.ufoo.stock.UFooStock;
import com.mmajdis.ufoo.util.Constants;
import com.mmajdis.ufoo.util.MatchResponse;
import com.mmajdis.ufoo.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author Matej Majdis
 *         <p>
 *         Main application class
 *         Called by every collected HTTP request [endpoint.collector.http]
 *         Looks up for relevant TCP info [endpoint.collector.tcp]
 *         Analyzes collected HTTP and TCP info [analyzer]
 *         Matches and Creates markers [analyzer]
 */
public class UFooProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(UFooProcessor.class);

    private PacketStream packetStream;

    private Serializer serializer;

    private FootprintSimilarityService footprintSimilarityService;

    private UFooStock uFooStock;

    public UFooProcessor(PacketStream packetStream, Serializer serializer, FootprintSimilarityService footprintSimilarityService, UFooStock uFooStock) {
        this.packetStream = packetStream;
        this.serializer = serializer;
        this.footprintSimilarityService = footprintSimilarityService;
        this.uFooStock = uFooStock;
    }

    public Result analyze(HTTPFootprint httpFootprint) {

        Set<TCPFootprint> tcpFootprints;
        if (packetStream == null) {
            tcpFootprints = null;
        } else {
            tcpFootprints = packetStream.getActualTcpStream().get(httpFootprint.getRequestInfo().getClientIp());
        }

        try {
            UFooEntity uFooEntity = serializer.serialize(httpFootprint, tcpFootprints);
            MatchResponse response = footprintSimilarityService.getNearestNeighbour(uFooEntity);

            //TODO apply relation data
            if (response.getDistance() >= Constants.DISTANCE_THRESHOLD) {
                uFooStock.addFirst(uFooEntity);
                LOGGER.info("---> New UFoo created, distance: {}", response.getDistance());
                return Result.CREATED;
            }

            long alertThreshold = getAlertThreshold(uFooEntity.getRelationData());
            int count = uFooStock.insertNext(response.getMatchedEntity(), uFooEntity);

            if (count > alertThreshold) {
                //TODO throw new RequestCountAlertException("Request count threshold exceeded by: " + uFooEntity);
                LOGGER.warn("---> Possible attack detected for UFoo: {}", uFooEntity);
                return Result.DETECTED;
            }
            LOGGER.info("---> Next UFoo inserted, distance: {}", response.getDistance());
            return Result.INSERTED;
        } catch (Exception ex) {
            LOGGER.error("Error while processing UFoo for: {}", httpFootprint);
            return Result.ERROR;
        }
    }

    private long getAlertThreshold(UFooEntity.RelationData relationData) {
        long threshold = 50;
        if (Constants.UNTRUSTED_COUNTRIES.contains(relationData.getCountry())) {
            return threshold - (threshold / 3);
        }
        return threshold; //TODO change
    }
}
