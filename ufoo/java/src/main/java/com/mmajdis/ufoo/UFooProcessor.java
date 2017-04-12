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


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    private List<String> distances;

    public UFooProcessor(PacketStream packetStream, Serializer serializer, FootprintSimilarityService footprintSimilarityService, UFooStock uFooStock) {
        this.packetStream = packetStream;
        this.serializer = serializer;
        this.footprintSimilarityService = footprintSimilarityService;
        this.uFooStock = uFooStock;
        this.distances = new ArrayList<>();
    }

    public synchronized Result analyze(HTTPFootprint httpFootprint) {

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

            if (Constants.TESTING_MODE) {
                markDistance(httpFootprint.getRequestInfo().getClientIp(), response.getDistance());
            }

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

    private void markDistance(String ip, double distance) {
        distances.add(ip + "," + String.valueOf(distance));
        if (distances.size() > 9) {
            try (FileWriter writer = new FileWriter("./src/main/webapp/analysis/distances.csv", true)) {
                String collect = distances.stream().collect(Collectors.joining("\n"));
                writer.write("\n");
                writer.write(collect);
                writer.close();
            } catch (IOException e) {
                LOGGER.warn("Can not write output distances !");
                return;
            }
            distances.clear();
        }
    }

    private long getAlertThreshold(UFooEntity.RelationData relationData) {
        long threshold = 3000;
        if (Constants.UNTRUSTED_COUNTRIES.contains(relationData.getCountry())) {
            return threshold - (threshold / 3);
        }
        return threshold; //TODO change
    }
}
