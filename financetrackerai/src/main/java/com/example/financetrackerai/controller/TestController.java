package com.example.financetrackerai.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public static String test(){
        return "Test";
    }
}
