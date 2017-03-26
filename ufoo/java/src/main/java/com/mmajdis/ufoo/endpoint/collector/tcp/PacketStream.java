package com.mmajdis.ufoo.endpoint.collector.tcp;

import com.mmajdis.ufoo.exception.PacketStreamException;
import com.mmajdis.ufoo.util.LRUFactory;
import org.apache.commons.lang3.SystemUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author Matej Majdis
 */
public class PacketStream implements Runnable{

    private RuntimeProcessor runtimeProcessor;

    private Parser parser;

    private Map<String, Set<TCPFootprint>> tcpStream;

    public PacketStream() {
        this.runtimeProcessor = new RuntimeProcessor();
        this.parser = new Parser();
        this.tcpStream = LRUFactory.createLRUMap(10);
    }

    @Override
    public void run() {
        start();
    }

    public void start() {

        InputStreamReader inputStreamReader = new InputStreamReader(runtimeProcessor.startTCPInputStream());
        try(BufferedReader input = new BufferedReader(inputStreamReader)) {

            processInput(input);

        } catch (IOException e) {
            throw new PacketStreamException("Error while reading TCP InputStream", e);
        }
    }

    private void processInput(BufferedReader input) throws IOException {

        String line;
        while ((line = input.readLine()) != null) {
            if (!isComplete(line)) {
                String next = input.readLine();
                if (next != null) {
                    line = line + next;
                }
            }

            Map.Entry<String, TCPFootprint> tcpFootprint = parser.parse(line);

            if (tcpFootprint != null) {
                addToMap(tcpFootprint);
            }
        }
    }

    private boolean isComplete(String line) {
        return !SystemUtils.IS_OS_LINUX || line.indexOf('>') != -1;
    }

    private void addToMap(Map.Entry<String, TCPFootprint> tcpFootprint) {

        final String IP = tcpFootprint.getKey();
        final Set<TCPFootprint> existing = tcpStream.get(IP);
        if (existing != null) {
            existing.add(tcpFootprint.getValue());
        } else {
            Set<TCPFootprint> newSet = LRUFactory.createLRUSet(2);
            newSet.add(tcpFootprint.getValue());
            tcpStream.put(IP, newSet);
        }
    }

    public Map<String, Set<TCPFootprint>> getActualTcpStream() {
        return Collections.unmodifiableMap(tcpStream);
    }
}