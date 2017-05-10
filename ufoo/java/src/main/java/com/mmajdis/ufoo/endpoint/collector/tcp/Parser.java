package com.mmajdis.ufoo.endpoint.collector.tcp;

import java.util.AbstractMap;
import java.util.Map;

/**
 * @author Matej Majdis
 */
class Parser {

    public Map.Entry<String, TCPFootprint> parse(String packetString) {

        packetString = packetString + ", ";
        String[] parsed = packetString.split("\\(", 2);
        if (parsed.length != 2) {
            return null;
        }

        final Long timestamp = parseTimestamp(parsed[0]);
        if (timestamp == null) {
            return null;
        }

        final Long id = parse(parsed[1], "id ", ',');
        final Long ipLength = parse(parsed[1], "length", ')');
        final Long tcpLength = parse(parsed[1], "length", true, ',');
        final Long tcpWindow = parse(parsed[1], "win ", ',');

        String IP = parseIP(parsed[1]);
        if (IP == null) {
            return null;
        }
        if (IP.matches(".*\\..*\\..*\\..*\\..*")) {
            int lastDot = IP.lastIndexOf(".");
            IP = IP.substring(0, lastDot);
        }

        return new AbstractMap.SimpleEntry<>(IP, new TCPFootprint(id, timestamp, ipLength, tcpLength, tcpWindow));
    }

    private String parseIP(String dataPart) {

        String[] parsed = dataPart.split(">", 2);
        if (parsed.length != 2) {
            return null;
        }
        String ipPart[] = parsed[0].split(" ");

        return ipPart[ipPart.length - 1];
    }

    private Long parse(String dataPart, String expression, char divider) {

        return parse(dataPart, expression, false, divider);
    }

    private Long parse(String dataPart, String expression, boolean useLast, char divider) {

        int idIndex = dataPart.indexOf(expression);
        if (useLast) {
            idIndex = dataPart.lastIndexOf(expression);
        }

        if (idIndex == -1) {
            return null;
        }

        int start = dataPart.indexOf(" ", idIndex) + 1;
        int end = dataPart.indexOf(" ", start);
        String substr = dataPart.substring(start, end);

        if (substr.charAt(substr.length() - 1) == divider) {
            substr = substr.substring(0, substr.length() - 1);
        }

        Long value;
        try {
            value = Long.valueOf(substr);
        } catch (NumberFormatException ex) {
            return null;
        }
        return value;
    }

    private Long parseTimestamp(String infoPart) {

        String[] info = infoPart.substring(0, infoPart.length() - 2).split(" ", 2);
        if (info.length != 2) {
            return null;
        }

        return Long.valueOf(info[0].split("\\.", 2)[0]);
    }
}
