package com.mmajdis.ufoo.endpoint;

import com.mmajdis.ufoo.endpoint.collector.tcp.PacketStream;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import javax.inject.Named;

/**
 * @author Matej Majdis
 */
@Aspect
@Named
public class Injector {

    @Pointcut(
            "within(@org.springframework.web.bind.annotation.RestController *) && " +
                    "execution(* *(..))"
    )
    public void controller() {
    }

    @Before("controller()")
    public void advice(JoinPoint thisJoinPoint) {

        System.out.println("--------" + thisJoinPoint);
        PacketStream packetStream = new PacketStream();
        packetStream.start();
        //TODO - do the magic
    }
}
