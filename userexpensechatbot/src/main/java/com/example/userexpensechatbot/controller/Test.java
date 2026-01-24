package com.example.userexpensechatbot.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping("/test")
    public String testAPI (){
        return "Test API";
    }
}
