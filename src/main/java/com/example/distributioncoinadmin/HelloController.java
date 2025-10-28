package com.example.distributioncoinadmin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Spring Boot is running. -_-";
    }
}