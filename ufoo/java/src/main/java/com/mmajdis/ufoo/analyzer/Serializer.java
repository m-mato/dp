package com.mmajdis.ufoo.analyzer;

import com.mmajdis.ufoo.UFooEntity;
import com.mmajdis.ufoo.endpoint.collector.http.HTTPFootprint;
import com.mmajdis.ufoo.endpoint.collector.tcp.TCPFootprint;

import java.util.Set;

/**
 * @author Matej Majdis
 *
 * Serialize collected information to Sketches of Markers
 */
public class Serializer {

    public UFooEntity serialize(HTTPFootprint httpFootprint) {

        return serialize(httpFootprint, null);
    }

    public UFooEntity serialize (HTTPFootprint httpFootprint, Set<TCPFootprint> tcpFootprints) {

        //TODO implement
        return null;
    }

    public HTTPFootprint deserializeHTTP(UFooEntity uFooEntity) {

        //TODO implement
        return null;
    }

    public TCPFootprint deserializeTCP(UFooEntity uFooEntity) {

        //TODO implement
        return null;
    }
}
