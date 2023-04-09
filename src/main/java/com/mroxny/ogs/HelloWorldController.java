package com.mroxny.ogs;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/checkHTTP")
    public String helloWorld(){
        return "Test string";
    }
}
