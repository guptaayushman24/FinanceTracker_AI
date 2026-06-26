package com.example.financetrackerai.service;

import com.example.financetrackerai.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    SignupResponsedto createUser(SignupRequestdto signupRequestdto);
    LoginResponsedto signInuser (LoginRequestdto loginRequestdto);
    List<UserDetailResponseDTO> userDetail ();
    EmailResponsedto findById (Integer id);
}
