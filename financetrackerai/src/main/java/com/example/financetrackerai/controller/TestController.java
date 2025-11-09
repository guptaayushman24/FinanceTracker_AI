package com.example.financetrackerai.controller;

import com.example.financetrackerai.security.ExtractToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    ExtractToken extractToken;

    @GetMapping("/test")
    public String test(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null) {
            System.out.println("User is not logged in !!");
            return "User is not logged in !!";
        }

        int userId = extractToken.extractUserId(authHeader);
        return "Test";
    }
}