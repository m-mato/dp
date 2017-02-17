package com.mmajdis.ufoo;



import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.lang3.SystemUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        String[] cmdLinux = {"/bin/bash", "-c", "tcpdump -U -n -i eth0 \"tcp\" -P in -S -tt -v"};
        String cmdWindows = "cmd /c tcpdump -i \"\\Device\\NPF_{D705A902-0D2E-4121-A88B-E7A6C582EAE7}\" -U -n -S -tt -v tcp";

        Process pb = null;

        try {
           if(SystemUtils.IS_OS_WINDOWS){
               pb = Runtime.getRuntime().exec(cmdWindows);
           } else {
               pb = Runtime.getRuntime().exec(cmdLinux);
           }

            String line;
            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
                processPacket(line);
            }
            input.close();
        } catch (IOException e) {
            return;
        }
    }

    private void processPacket(String line) {

    }


    @Override
    public void run() {
    }
}