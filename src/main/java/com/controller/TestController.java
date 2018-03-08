package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by maciej on 08.03.18.
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testMethod() {
        return "controller works";
    }

}
