package com.example.myds.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "faeturev3";
    }
    @GetMapping("/time")
    public String time() {
        return java.time.LocalDateTime.now().toString();
    }
}
