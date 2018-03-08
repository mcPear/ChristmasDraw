package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by maciej on 08.03.18.
 */
@Controller
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public String testMethod(){
        return "controller works";
    }

}
