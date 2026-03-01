package com.example.financetrackerai.service;

import com.example.financetrackerai.dto.*;

import java.util.List;

public interface  FetchUserSignupDataService {
    public StoreSignupData fetchUserSignUpData(StoreSignupData storeSignupData);
    public SignupResponsedto userSignupData (String emailAddress);
    public ProfileResponseDto profileResponse (ProfileRequestDto profileRequestDto);
    List<UserRegisteredExpensedto> userRegisteredExpense (Integer userId);
}
