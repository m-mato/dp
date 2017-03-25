package com.mmajdis.ufoo;

import com.mmajdis.ufoo.analyzer.FootprintSimilarityService;
import com.mmajdis.ufoo.analyzer.Serializer;
import com.mmajdis.ufoo.endpoint.collector.http.HTTPFootprint;
import com.mmajdis.ufoo.endpoint.collector.tcp.PacketStream;
import com.mmajdis.ufoo.endpoint.collector.tcp.TCPFootprint;
import com.mmajdis.ufoo.stock.UFooStock;
import com.mmajdis.ufoo.util.MatchResponse;
import com.mmajdis.ufoo.util.Result;

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
            double distanceThreshold = 0.4; //TODO change
            if (response.getDistance() >= distanceThreshold) {
                uFooStock.addFirst(uFooEntity);
                return Result.CREATED;
            }

            long alertThreshold = 50; //TODO change
            int count = uFooStock.insertNext(response.getMatchedEntity(), uFooEntity);
            int i = 0;
            if (count > alertThreshold) {
                //TODO throw new RequestCountAlertException("Request count threshold exceeded by: " + uFooEntity);
                return Result.DETECTED;
            }
            return Result.INSERTED;
        } catch (Exception ex) {
            return Result.ERROR;
        }
    }
}
