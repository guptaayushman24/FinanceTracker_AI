package com.example.financetrackerai.service;

import com.example.financetrackerai.dto.LoginRequestdto;
import com.example.financetrackerai.dto.LoginResponsedto;
import com.example.financetrackerai.dto.SignupRequestdto;
import com.example.financetrackerai.dto.SignupResponsedto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    SignupResponsedto createUser(SignupRequestdto signupRequestdto);
    LoginResponsedto signInuser (LoginRequestdto loginRequestdto);
}
