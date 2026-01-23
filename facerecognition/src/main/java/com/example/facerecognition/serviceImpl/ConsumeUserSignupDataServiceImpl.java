package com.example.facerecognition.serviceImpl;

import com.example.facerecognition.dto.UserSignupData;
import com.example.facerecognition.service.ConsumeUserSignupDataService;
import org.springframework.beans.factory.annotation.Autowired;

public class ConsumeUserSignupDataServiceImpl implements ConsumeUserSignupDataService {
    @Override
    public UserSignupData consumeUserSignupData(UserSignupData userSignupData) {
        return userSignupData;
    }
}
