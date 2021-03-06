package com.mmajdis.ufoo.analyzer;

import com.mmajdis.ufoo.UFooEntity;
import com.mmajdis.ufoo.endpoint.collector.http.HTTPFootprint;
import com.mmajdis.ufoo.endpoint.collector.http.geoip.Location;
import com.mmajdis.ufoo.endpoint.collector.tcp.TCPFootprint;

import java.util.*;

import static com.mmajdis.ufoo.util.Constants.MAX_UNKNOWN_HEADERS;
import static com.mmajdis.ufoo.util.Constants.SEPARATOR;
import static com.mmajdis.ufoo.util.Constants.UNDEFINED;

/**
 * @author Matej Majdis
 *         Serialize collected information to Sketches of Markers
 */
public class Serializer {

    public UFooEntity serialize(HTTPFootprint httpFootprint) {

        return serialize(httpFootprint, null);
    }

    public UFooEntity serialize(HTTPFootprint httpFootprint, Set<TCPFootprint> tcpFootprints) {

        UFooEntity uFooEntity = new UFooEntity();
        uFooEntity.setStaticData(buildStaticData(httpFootprint, tcpFootprints));
        uFooEntity.setRelationData(buildRelationData(httpFootprint, tcpFootprints));

        return uFooEntity;
    }

    private String buildStaticData(HTTPFootprint httpFootprint, Set<TCPFootprint> tcpFootprints) {

        //headers + ip + location + encoding + locales + path + tcpWindow + length

        HTTPFootprint.RequestInfo requestInfo = httpFootprint.getRequestInfo();

        return String.join(SEPARATOR,
                buildHeaders(httpFootprint.getHeaders()),
                (requestInfo.getClientIp() == null || requestInfo.getClientIp().isEmpty()) ? UNDEFINED : requestInfo.getClientIp(),
                buildLocation(requestInfo.getLocation()),
                (requestInfo.getEncoding() == null || requestInfo.getEncoding().isEmpty()) ? UNDEFINED : requestInfo.getEncoding(),
                buildLocales(requestInfo.getLocales()),
                (requestInfo.getServletPath() == null || requestInfo.getServletPath().isEmpty()) ? UNDEFINED : requestInfo.getServletPath(),
                buildTCPInfo(tcpFootprints)
        );
    }

    private String buildHeaders(Map<String, String> headers) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        StringJoiner stringJoiner = new StringJoiner(SEPARATOR);
        for (String header : HeadersDefinition.getStaticHeaders()) {
            String headerValue = headers.get(header.toLowerCase());
            if (headerValue == null) {
                headerValue = headers.get(header);
            }
            if (headerValue != null && !headerValue.isEmpty()) {
                stringJoiner.add(headerValue);
                headers.remove(header);
                headers.remove(header.toLowerCase());
            } else {
                stringJoiner.add(UNDEFINED);
            }
        }
        stringJoiner.add(buildUnknownHeaders(headers));
        return "[" + stringJoiner.toString() + "]";
    }

    private String buildUnknownHeaders(Map<String, String> headers) {
        StringJoiner stringJoiner = new StringJoiner(SEPARATOR);
        Iterator<String> iterator = headers.keySet().iterator();
        int counter = 0;
        while (iterator.hasNext() && counter < MAX_UNKNOWN_HEADERS) {
            String headerValue = headers.get(iterator.next());
            if (headerValue != null && !headerValue.isEmpty()) {
                stringJoiner.add(headerValue);
                counter++;
            }
        }
        return stringJoiner.toString();
    }

    private String buildLocation(Location location) {
        if (location == null) {
            return UNDEFINED + SEPARATOR + UNDEFINED;
        }
        String country = (location.getCountry() == null || location.getCountry().isEmpty()) ? UNDEFINED : location.getCountry();
        String city = (location.getCity() == null || location.getCity().isEmpty()) ? UNDEFINED : location.getCity();
        return country + SEPARATOR + city;
    }

    private String buildLocales(Set<String> locales) {
        if (locales == null || locales.isEmpty()) {
            return UNDEFINED;
        }
        StringBuilder sb = new StringBuilder();
        locales.forEach(locale -> {
            String localeChecked = (locale == null || locale.isEmpty()) ? "" : locale;
            sb.append(localeChecked);
        });
        return (sb.length() == 0) ? UNDEFINED : sb.toString();
    }

    private String buildTCPInfo(Set<TCPFootprint> tcpFootprints) {
        if (tcpFootprints == null || tcpFootprints.isEmpty()) {
            return UNDEFINED + SEPARATOR + UNDEFINED;
        }
        Long tcpWindow = null;
        Long length = null;

        Iterator<TCPFootprint> iterator = tcpFootprints.iterator();
        while ((tcpWindow == null || length == null) && iterator.hasNext()) {
            TCPFootprint tcpFootprint = iterator.next();
            tcpWindow = tcpFootprint.getTcpWindow();
            length = tcpFootprint.getTcpLength();
        }

        String tcpWindowString = (tcpWindow == null) ? UNDEFINED : String.valueOf(tcpWindow);
        String lengthString = (length == null) ? UNDEFINED : String.valueOf(length);

        return tcpWindowString + SEPARATOR + lengthString;
    }

    private UFooEntity.RelationData buildRelationData(HTTPFootprint httpFootprint, Set<TCPFootprint> tcpFootprints) {
        UFooEntity.RelationData relationData = new UFooEntity.RelationData();
        Location location = httpFootprint.getRequestInfo().getLocation();
        if (location != null) {
            relationData.setCountry(location.getCountry());
        }
        relationData.setRelationHeaders(buildRelationHeaders(httpFootprint.getHeaders()));
        relationData.setTimestamp(buildTimestamp(tcpFootprints));
        return relationData;
    }

    private Map<String, String> buildRelationHeaders(Map<String, String> headers) {
        if (headers == null || headers.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, String> relationHeaders = new HashMap<>();
        for (String header : HeadersDefinition.getRelationHeaders()) {
            String headerValue = headers.get(header.toLowerCase());
            if (headerValue == null) {
                headerValue = headers.get(header);
            }
            if (headerValue != null) {
                relationHeaders.put(header, headerValue);
            }
        }
        return relationHeaders;
    }

    private long buildTimestamp(Set<TCPFootprint> tcpFootprints) {
        Long timestamp = null;
        if (tcpFootprints == null) {
            return -1;
        }
        for (TCPFootprint tcpFootprint : tcpFootprints) {
            if (timestamp == null) {
                timestamp = tcpFootprint.getTimestamp();
            } else if (tcpFootprint.getTimestamp() != null &&
                    tcpFootprint.getTimestamp() > timestamp) {
                timestamp = tcpFootprint.getTimestamp();
            }
        }
        return (timestamp == null) ? -1 : timestamp;
    }
}
