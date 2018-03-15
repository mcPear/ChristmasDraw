package com;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by maciej on 15.03.18.
 */
@RestController("/api")
public class ExampleController {

    @GetMapping("/example")
    public String method() {
        return "example";
    }

}
