package com.mmajdis.ufoo;

import com.mmajdis.ufoo.endpoint.collector.http.HTTPFootprint;
import com.mmajdis.ufoo.util.Response;

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

    public Response run(HTTPFootprint httpFootprint) {
        return Response.ERROR;
    }
}
