package com.mmajdis.ufoo.endpoint.collector.tcp;

import com.mmajdis.ufoo.exception.PacketStreamException;
import org.apache.commons.lang3.SystemUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Matej Majdis
 */
class RuntimeProcessor {

    public InputStream startTCPInputStream() {

        String[] cmdLinux = {"/bin/bash", "-c", "tcpdump -U -n -ieth0 \"tcp\" -P in -S -tt -v"};
        String cmdWindows = "cmd /c tcpdump -i \"\\Device\\NPF_{D705A902-0D2E-4121-A88B-E7A6C582EAE7}\" -U -n -S -tt -v tcp";

        Process pb;

        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                pb = Runtime.getRuntime().exec(cmdWindows);
            } else {
                pb = Runtime.getRuntime().exec(cmdLinux);
            }

            return pb.getInputStream();
        } catch (IOException e) {
            throw new PacketStreamException("Unable to start process - TCP", e);
        }
    }
}
