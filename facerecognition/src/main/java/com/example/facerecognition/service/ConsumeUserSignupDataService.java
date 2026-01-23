package com.example.facerecognition.service;

import com.example.facerecognition.dto.UserSignupData;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "financetrackerai",
        url = "http://localhost:8080/sendUserSignupData"
)
public interface ConsumeUserSignupDataService {
    public UserSignupData consumeUserSignupData(UserSignupData userSignupData);
}
