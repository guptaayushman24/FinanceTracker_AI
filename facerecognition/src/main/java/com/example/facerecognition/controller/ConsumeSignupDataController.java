package com.example.facerecognition.controller;

import com.example.facerecognition.dto.UserSignupData;
import com.example.facerecognition.service.ConsumeUserSignupDataService;
import com.example.facerecognition.serviceImpl.ConsumeUserSignupDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumeSignupDataController {
    @Autowired
    ConsumeUserSignupDataService consumeUserSignupDataService;
    public UserSignupData consumeUserSignupData(@RequestBody UserSignupData userSignupData){
        return consumeUserSignupDataService.consumeUserSignupData(userSignupData);
    }
}
