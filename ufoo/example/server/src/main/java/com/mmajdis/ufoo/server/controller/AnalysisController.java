package com.mmajdis.ufoo.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Matej Majdis
 */
@Controller
@RequestMapping("/analysis")
public class AnalysisController {

    @RequestMapping(value = "/histogram", method = RequestMethod.GET)
    public String test(final HttpServletRequest request) throws InterruptedException {

        return "analysis/histogram";
    }
}
