package com.example.facerecognition.service;

import com.example.facerecognition.dto.SignupRequestdto;
import com.example.facerecognition.dto.SignupResponsedto;
import com.example.facerecognition.dto.UserSignupData;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        value = "consumer-service",
        url = "http://localhost:8080"
)
public interface ConsumeUserSignupDataService {
    @GetMapping("/auth/getUserSignupData")
    SignupResponsedto getSignupData (@RequestParam("emailAddress") String emailAddress);
}
