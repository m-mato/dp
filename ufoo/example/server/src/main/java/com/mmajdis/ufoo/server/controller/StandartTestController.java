package com.mmajdis.ufoo.server.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author Matej Majdis
 */
@RestController
@RequestMapping("/test")
public class StandartTestController {

    @RequestMapping(value = "/short", method = RequestMethod.GET)
    public String resourceShort(final HttpServletRequest request) throws InterruptedException {

        final int low = 100;
        final int high = 700;
        long timeout = getTimeout(low, high);

        TimeUnit.MILLISECONDS.sleep(timeout);
        return "OK !";
    }

    @RequestMapping(value = "/medium", method = RequestMethod.GET)
    public String resourceMedium(final HttpServletRequest request) throws InterruptedException {

        final int low = 500;
        final int high = 1500;
        long timeout = getTimeout(low, high);

        TimeUnit.MILLISECONDS.sleep(timeout);
        return "OK !";
    }

    @RequestMapping(value = "/long", method = RequestMethod.GET)
    public String resourceLong(final HttpServletRequest request) throws InterruptedException {

        final int low = 1000;
        final int high = 5000;
        long timeout = getTimeout(low, high);

        TimeUnit.MILLISECONDS.sleep(timeout);
        return "OK !";
    }

    private long getTimeout(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low) + low;
    }
}
