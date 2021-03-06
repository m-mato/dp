package com.mmajdis.ufoo.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Matej Majdis
 */
@Controller
@RequestMapping("/analysis")
public class AnalysisController {

    @RequestMapping(value = "/histogram", method = RequestMethod.GET)
    public String histogram(final HttpServletRequest request) throws InterruptedException {

        return "analysis/histogram";
    }

    @RequestMapping(value = "/flow", method = RequestMethod.GET)
    public String flow(final HttpServletRequest request) throws InterruptedException {

        return "analysis/flow";
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView analysis(final HttpServletRequest request) throws InterruptedException {

        return new ModelAndView("analysis");
    }
}
