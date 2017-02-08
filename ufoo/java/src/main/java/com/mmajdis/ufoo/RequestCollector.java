package com.mmajdis.ufoo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import javax.inject.Named;

/**
 * @author Matej Majdis <matej.majdis@avg.com>
 */
@Aspect
@Named
public class RequestCollector {

    @Pointcut(
            "within(@org.springframework.web.bind.annotation.RestController *) && " +
                    "execution(* *(..))"
    )
    public void controller() {
    }

    @Before("controller()")
    public void advice(JoinPoint thisJoinPoint) {

        System.out.println("--------" + thisJoinPoint);
        //TODO - do the magic
    }
}
