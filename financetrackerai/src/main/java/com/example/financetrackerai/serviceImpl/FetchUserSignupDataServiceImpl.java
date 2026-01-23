package com.example.financetrackerai.serviceImpl;

import com.example.financetrackerai.dto.SignupResponsedto;
import com.example.financetrackerai.dto.StoreSignupData;
import com.example.financetrackerai.service.FetchUserSignupDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FetchUserSignupDataServiceImpl implements FetchUserSignupDataService{
    @Override
    public StoreSignupData fetchUserSignUpData(StoreSignupData storeSignupData) {
        return storeSignupData;
    }
}
