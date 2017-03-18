package com.mmajdis.ufoo;

import com.mmajdis.ufoo.analyzer.FootprintMatcher;
import com.mmajdis.ufoo.analyzer.Serializer;
import com.mmajdis.ufoo.endpoint.collector.http.HTTPFootprint;
import com.mmajdis.ufoo.endpoint.collector.tcp.PacketStream;
import com.mmajdis.ufoo.endpoint.collector.tcp.TCPFootprint;
import com.mmajdis.ufoo.stock.UFooStockManager;
import com.mmajdis.ufoo.util.Response;

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

    private FootprintMatcher footprintMatcher;

    private UFooStockManager UFooStockManager;

    public UFooProcessor(PacketStream packetStream, Serializer serializer, FootprintMatcher footprintMatcher, UFooStockManager UFooStockManager) {
        this.packetStream = packetStream;
        this.serializer = serializer;
        this.footprintMatcher = footprintMatcher;
        this.UFooStockManager = UFooStockManager;
    }

    public Response run(HTTPFootprint httpFootprint) {

        Set<TCPFootprint> tcpFootprints;
        if (packetStream == null) {
            tcpFootprints = null;
        } else {
            tcpFootprints = packetStream.getActualTcpStream().get(httpFootprint.getRequestInfo().getClientIp());
        }

        try {
            String sketch = serializer.serialize(httpFootprint, tcpFootprints);
            double result = footprintMatcher.match(sketch);

            if (result < 0) {
                //TODO create
                return Response.CREATED;
            }

            return null; //TODO Response.INSERTED || Response.DETECTED;
        } catch (Exception ex) {
            return Response.ERROR;
        }
    }
}
