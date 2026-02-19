package com.example.financetrackerai.serviceImpl;

import com.example.financetrackerai.dto.ProfileRequestDto;
import com.example.financetrackerai.dto.ProfileResponseDto;
import com.example.financetrackerai.dto.SignupResponsedto;
import com.example.financetrackerai.dto.StoreSignupData;
import com.example.financetrackerai.model.UserModel;
import com.example.financetrackerai.repository.UserRepository;
import com.example.financetrackerai.service.FetchUserSignupDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FetchUserSignupDataServiceImpl implements FetchUserSignupDataService{
    @Autowired
    UserRepository userRepository;
    @Override
    public StoreSignupData fetchUserSignUpData(StoreSignupData storeSignupData) {
        return storeSignupData;
    }

    @Override
    public SignupResponsedto userSignupData(String emailAddress) {
        SignupResponsedto signupResponsedto = new SignupResponsedto();
        UserModel userModel = userRepository.findByemailAddress(emailAddress);
        signupResponsedto.setFirstName(userModel.getFirstName());
        signupResponsedto.setLastName(userModel.getLastName());
        signupResponsedto.setEmailAddress(userModel.getEmailAddress());
        signupResponsedto.setUserPassword(userModel.getPassword());
        signupResponsedto.setUserExpense(userModel.getUser_expense());

        return signupResponsedto;
    }

    @Override
    public ProfileResponseDto profileResponse(ProfileRequestDto profileRequestDto) {
        ProfileResponseDto profileResponseDto = new ProfileResponseDto();
        UserModel userModel = userRepository.findByemailAddress(profileRequestDto.getEmailAddress());
        profileResponseDto.setFirstName(userModel.getFirstName());
        profileResponseDto.setLastName(userModel.getLastName());

        return profileResponseDto;
    }
}
