package com.mmajdis.ufoo;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.lang3.SystemUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author Matej Majdis
 */
public class PacketStream implements Runnable {

    Queue<Map<String, List<TCPFootprint>>> tcpStream;

    public PacketStream() {
        tcpStream = new CircularFifoQueue<>(10);
    }

    /**
     * Main startup method
     */
    public void start() {
        String[] cmdLinux = {"/bin/bash", "-c", "tcpdump -e -U -n -ieth0 \"tcp\" -P in -S -tt -v"};
        String cmdWindows = "cmd /c tcpdump -i \"\\Device\\NPF_{D705A902-0D2E-4121-A88B-E7A6C582EAE7}\" -U -n -S -tt -v tcp";

        Process pb;

        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                pb = Runtime.getRuntime().exec(cmdWindows);
            } else {
                pb = Runtime.getRuntime().exec(cmdLinux);
            }

            String line;
            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            while ((line = input.readLine()) != null) {
                Map.Entry<String, TCPFootprint> tcpFootprint = processPacket(line);
                System.out.println(line);
            }
            input.close();
        } catch (IOException e) {
            return;
        }
    }

    private Map.Entry<String, TCPFootprint> processPacket(String line) {

        line = line + ", ";
        String[] parsed = line.split("\\(", 2);
        if (parsed.length != 2) {
            return null;
        }

        final Long timestamp = parseTimestamp(parsed[0]);
        if (timestamp == null) {
            return null;
        }

        final Long id = parse(parsed[1], "id ", ',');
        final Long ipLength = parse(parsed[1], "length", ')');
        final Long tcpLength = parse(parsed[1], "length ", true, ',');
        final Long tcpWindow = parse(parsed[1], "win ", ',');

        final String IP = parseIP(parsed[1]);
        if (IP == null) {
            return null;
        }

        return new AbstractMap.SimpleEntry<>(IP, new TCPFootprint(id, timestamp, ipLength, tcpLength, tcpWindow));
    }

    private String parseIP(String dataPart) {

        String[] parsed = dataPart.split(">", 2);
        if(parsed.length != 2) {
            return null;
        }
        String ipPart[] = parsed[0].split(" ");

        return ipPart[ipPart.length -1];
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

        return Long.valueOf(substr);
    }

    private Long parseTimestamp(String infoPart) {

        String[] info = infoPart.substring(0, infoPart.length() - 2).split(" ", 2);
        if (info.length != 2) {
            return null;
        }

        return Long.valueOf(info[0].split("\\.", 2)[0]);
    }


    @Override
    public void run() {
    }
}