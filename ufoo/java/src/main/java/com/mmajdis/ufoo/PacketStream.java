package com.mmajdis.ufoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Matej Majdis
 */
public class PacketStream implements Runnable {

    /**
     * Main startup method
     */
    public void start() {
        String[] cmd = {"/bin/bash", "-c", "tcpdump -e -U -n -ieth0 \"tcp\" -P in -S -tt -v"};
        Process pb = null;

        try {
            pb = Runtime.getRuntime().exec(cmd);

            String line;
            BufferedReader input = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();
        } catch (IOException e) {
            return;
        }
    }


    @Override
    public void run() {
    }
}