package com.fooddelivery.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
@CrossOrigin(origins = "http://ec2-98-84-110-225.compute-1.amazonaws.com:3000", allowCredentials = "true")
public class TestController {

    @GetMapping("ping")
    public String ping() {
        return "Backend is running!";
    }
}
