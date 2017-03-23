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
 *
 * Main application class
 * Called by every collected HTTP request [endpoint.collector.http]
 * Looks up for relevant TCP info [endpoint.collector.tcp]
 * Analyzes collected HTTP and TCP info [analyzer]
 * Matches and Creates markers [analyzer]
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

            if (response.getSimilarity() == 0) {
                //TODO create
                return Result.CREATED;
            }

            return null; //TODO Result.INSERTED || Result.DETECTED;
        } catch (Exception ex) {
            return Result.ERROR;
        }
    }
}
