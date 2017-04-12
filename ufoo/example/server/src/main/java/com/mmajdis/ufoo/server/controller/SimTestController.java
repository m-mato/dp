package com.mmajdis.ufoo.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Matej Majdis
 */
@RestController
@RequestMapping("/test")
public class SimTestController {

    @RequestMapping(value = "/sim", method = RequestMethod.POST)
    public String sim(final HttpServletRequest request) throws InterruptedException {

        final int low = 100;
        final int high = 5000;
        long timeout = getTimeout(low, high);

        TimeUnit.MILLISECONDS.sleep(timeout);
        return "Sim OK !";
    }

    private long getTimeout(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low) + low;
    }
}