package com.example.facerecognition.service;

import com.example.facerecognition.dto.SignupRequestdto;
import com.example.facerecognition.dto.SignupResponsedto;
import com.example.facerecognition.dto.UserSignupData;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

//@FeignClient(
//        value = "consumer-service",
//        url = "http://localhost:8080"
//)
public interface ConsumeUserSignupDataService {
    SignupResponsedto getSignupData (String emailAddress);
}
