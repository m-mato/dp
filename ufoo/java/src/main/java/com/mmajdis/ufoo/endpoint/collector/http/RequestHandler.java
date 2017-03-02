package com.mmajdis.ufoo.endpoint.collector.http;

import com.mmajdis.ufoo.UFooProcessor;
import com.mmajdis.ufoo.endpoint.collector.http.geoip.Location;
import com.mmajdis.ufoo.endpoint.collector.http.geoip.LocationLookupService;
import com.mmajdis.ufoo.util.Response;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Matej Majdis
 *
 * Handles HTTP requests
 * Calls UFooProcessor for every request
 */
public class RequestHandler {

    private UFooProcessor uFooProcessor;

    private LocationLookupService locationLookupService;

    public RequestHandler(UFooProcessor uFooProcessor, LocationLookupService locationLookupService) {
        this.uFooProcessor = uFooProcessor;
        this.locationLookupService = locationLookupService;
    }

    public boolean handle(HttpServletRequest request) {

        if(request == null) {
            return false;
        }

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        Location location = locationLookupService.getLocation(ipAddress);

        Set<String> locales = new HashSet<>();
        Enumeration<Locale> requestLocales = request.getLocales();
        while (requestLocales.hasMoreElements()) {
            Locale locale = requestLocales.nextElement();
            locales.add(locale.getISO3Country());
        }

        Map<String, String> headers = new TreeMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            headers.put(key, value);
        }

        HTTPFootprint.RequestInfo requestInfo = new HTTPFootprint.RequestInfo(
                request.getServletPath(),
                ipAddress,
                location,
                locales,
                request.getCharacterEncoding()
        );

        HTTPFootprint httpFootprint = new HTTPFootprint(requestInfo, headers);

        Response response = uFooProcessor.run(httpFootprint);
        if(response.equals(Response.DETECTED)) {
            //TODO - reaction
            return true;
        }

        return !response.equals(Response.ERROR);
    }
}
