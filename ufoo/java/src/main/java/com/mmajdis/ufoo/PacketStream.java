package com.mmajdis.ufoo;

import org.apache.commons.lang.SystemUtils;

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
        String[] cmdLinux = {"/bin/bash", "-c", "tcpdump -e -U -n -ieth0 \"tcp\" -P in -S -tt -v"};
        String cmdWindows = "cmd /c tcpdump -i \"\\Device\\NPF_{D705A902-0D2E-4121-A88B-E7A6C582EAE7}\"  -e -U -n -S -tt -v";

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