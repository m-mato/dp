package com.mmajdis.ufoo.endpoint.collector.tcp;

import com.mmajdis.ufoo.exception.PacketStreamException;
import com.mmajdis.ufoo.util.Constants;
import org.apache.commons.lang3.SystemUtils;
import org.aspectj.apache.bcel.classfile.Constant;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Matej Majdis
 */
class RuntimeProcessor {

    public InputStream startTCPInputStream() {

        String[] cmdLinux = {"/bin/bash", "-c", "tcpdump -U -n -ieth0 \"tcp\" -Q in -S -tt -v"};
        String[] cmdLinuxTest = {"/bin/bash", "-c", "tcpdump -U -n -ieth1 \"tcp\" -Q in -S -tt -v"};
        String cmdWindows = "cmd /c tcpdump -i \"\\Device\\NPF_{D705A902-0D2E-4121-A88B-E7A6C582EAE7}\" -U -n -S -tt -v tcp";

        Process pb;

        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                pb = Runtime.getRuntime().exec(cmdWindows);
            } else {
                if(!Constants.TESTING_MODE) {
                    pb = Runtime.getRuntime().exec(cmdLinux);
                } else {
                    pb = Runtime.getRuntime().exec(cmdLinuxTest);
                }
            }

            return pb.getInputStream();
        } catch (IOException e) {
            throw new PacketStreamException("Unable to start process - TCP", e);
        }
    }
}
