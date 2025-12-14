package com.example.emailservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailTest {
    @GetMapping("/emailtest")
    public String testEmail(){
        return "Email Sent";
    }
}
