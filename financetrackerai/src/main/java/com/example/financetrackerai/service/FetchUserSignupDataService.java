package com.example.financetrackerai.service;

import com.example.financetrackerai.dto.SignupResponsedto;
import com.example.financetrackerai.dto.StoreSignupData;

import java.util.List;

public interface FetchUserSignupDataService {
    public StoreSignupData fetchUserSignUpData(StoreSignupData storeSignupData);
}
