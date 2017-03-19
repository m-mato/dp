package com.mmajdis.ufoo.analyzer;

import com.mmajdis.ufoo.UFooEntity;
import com.mmajdis.ufoo.endpoint.collector.http.HTTPFootprint;
import com.mmajdis.ufoo.endpoint.collector.http.geoip.Location;
import com.mmajdis.ufoo.endpoint.collector.tcp.TCPFootprint;

import java.util.*;

/**
 * @author Matej Majdis
 *         <p>
 *         Serialize collected information to Sketches of Markers
 */
public class Serializer {

    private static final String SEPARATOR = "|";
    public static final String EMPTY_ARR = "[]";

    public UFooEntity serialize(HTTPFootprint httpFootprint) {

        return serialize(httpFootprint, null);
    }

    public UFooEntity serialize(HTTPFootprint httpFootprint, Set<TCPFootprint> tcpFootprints) {

        UFooEntity uFooEntity = new UFooEntity();
        uFooEntity.setStaticData(buildStaticData(httpFootprint, tcpFootprints));
        uFooEntity.setRelationData(buildRelationData(httpFootprint, tcpFootprints));

        //TODO implement
        return uFooEntity;
    }


    public HTTPFootprint deserializeHTTP(UFooEntity uFooEntity) {

        //TODO implement
        return null;
    }

    public TCPFootprint deserializeTCP(UFooEntity uFooEntity) {

        //TODO implement
        return null;
    }

    private String buildStaticData(HTTPFootprint httpFootprint, Set<TCPFootprint> tcpFootprints) {

        //headers + ip + location + encoding + locales + path + tcpWindow + length

        HTTPFootprint.RequestInfo requestInfo = httpFootprint.getRequestInfo();

        return String.join(SEPARATOR,
                buildHeaders(httpFootprint.getHeaders()),
                requestInfo.getClientIp(),
                buildLocation(requestInfo.getLocation()),
                requestInfo.getEncoding(),
                buildLocales(requestInfo.getLocales()),
                requestInfo.getServletPath(),
                buildTCPInfo(tcpFootprints)
        );
    }

    private String buildHeaders(Map<String, String> headers) {
        if (headers == null) {
            return EMPTY_ARR;
        }
        StringJoiner stringJoiner = new StringJoiner(SEPARATOR);
        for (String header : HeadersDefinition.getStaticHeaders()) {
            String headerValue = headers.get(header.toLowerCase());
            if(headerValue == null) {
                headerValue = headers.get(header);
            }
            if (headerValue != null) {
                stringJoiner.add(headerValue);
                headers.remove(header);
            } else {
                stringJoiner.add("");
            }
        }
        stringJoiner.add(hash(headers));
        return "[" + stringJoiner.toString() + "]";
    }

    private String hash(Map<String, String> headers) {
        //TODO implement hash
        return String.valueOf(headers.hashCode());
    }

    private String buildLocation(Location location) {
        if (location == null) {
            return SEPARATOR;
        }
        String country = (location.getCountry() == null) ? "" : location.getCountry();
        String city = (location.getCity() == null) ? "" : location.getCity();
        return country + SEPARATOR + city;
    }

    private String buildLocales(Set<String> locales) {
        if (locales == null) {
            return EMPTY_ARR;
        }
        StringJoiner stringJoiner = new StringJoiner(SEPARATOR);
        locales.forEach(locale -> {
            String localeChecked = (locale == null) ? "" : locale;
            stringJoiner.add(localeChecked);
        });
        return "[" + stringJoiner.toString() + "]";
    }

    private String buildTCPInfo(Set<TCPFootprint> tcpFootprints) {
        if (tcpFootprints == null || tcpFootprints.isEmpty()) {
            return SEPARATOR;
        }
        Long tcpWindow = null;
        Long length = null;

        Iterator<TCPFootprint> iterator = tcpFootprints.iterator();
        while((tcpWindow == null || length == null) && iterator.hasNext()) {
            TCPFootprint tcpFootprint = iterator.next();
            tcpWindow = tcpFootprint.getTcpWindow();
            length = tcpFootprint.getTcpLength();
        }

        String tcpWindowString = (tcpWindow == null) ? "" : String.valueOf(tcpWindow);
        String lengthString = (length == null) ? "" : String.valueOf(length);

        return tcpWindowString + SEPARATOR + lengthString;
    }

    private UFooEntity.RelationData buildRelationData(HTTPFootprint httpFootprint, Set<TCPFootprint> tcpFootprints) {
        UFooEntity.RelationData relationData = new UFooEntity.RelationData();
        Location location = httpFootprint.getRequestInfo().getLocation();
        if(location != null) {
            relationData.setCountry(location.getCountry());
        }
        relationData.setRelationHeaders(buildRelationHeaders(httpFootprint.getHeaders()));
        relationData.setTimestamp(buildTimestamp(tcpFootprints));
        return relationData;
    }

    private Map<String, String> buildRelationHeaders(Map<String, String> headers) {
        if(headers == null || headers.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, String> relationHeaders= new HashMap<>();
        for(String header : HeadersDefinition.getRelationHeaders()) {
            String headerValue = headers.get(header.toLowerCase());
            if(headerValue == null) {
                headerValue = headers.get(header);
            }
            if(headerValue != null) {
                relationHeaders.put(header, headerValue);
            }
        }
        return relationHeaders;
    }

    private long buildTimestamp(Set<TCPFootprint> tcpFootprints) {
        Long timestamp = null;
        if(tcpFootprints == null) {
            return -1;
        }
        for(TCPFootprint tcpFootprint : tcpFootprints) {
            if(timestamp == null) {
                timestamp = tcpFootprint.getTimestamp();
            } else if(tcpFootprint.getTimestamp() != null &&
                    tcpFootprint.getTimestamp() > timestamp) {
                timestamp = tcpFootprint.getTimestamp();
            }
        }
        return (timestamp == null) ? -1 : timestamp;
    }
}
