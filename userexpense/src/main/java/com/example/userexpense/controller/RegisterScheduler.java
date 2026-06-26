package com.example.userexpense.controller;

import com.example.userexpense.dto.RegisterSchedulerRequestdto;
import com.example.userexpense.dto.RegisterSchedulerResponsedto;
import com.example.userexpense.security.ExtractUserId;
import com.example.userexpense.serviceImpl.RegisterSchedulerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterScheduler {
    @Autowired
    RegisterSchedulerImpl registerSchedulerImpl;
    @Autowired
    ExtractUserId extractUserId;
    @PostMapping("/registerscheduler")
    public RegisterSchedulerResponsedto registerScheduler (@RequestBody RegisterSchedulerRequestdto registerSchedulerRequestdto,@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();

        return registerSchedulerImpl.registerEvent(registerSchedulerRequestdto,userId);
    }

    @PostMapping("/deletescheduler")
    public RegisterSchedulerResponsedto deleteRegisterScheduler (@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();

        return registerSchedulerImpl.deleteRegisterScheduler(userId);

    }
}
