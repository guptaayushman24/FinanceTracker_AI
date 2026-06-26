package com.example.financetrackerai.serviceImpl;

import com.example.financetrackerai.dto.*;
import com.example.financetrackerai.model.UserModel;
import com.example.financetrackerai.repository.UserRepository;
import com.example.financetrackerai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public SignupResponsedto createUser(SignupRequestdto signupRequestdto) {
        UserModel userModel = new UserModel();
        userModel.setEmailAddress(signupRequestdto.getEmailAddress());
        userModel.setFirstName(signupRequestdto.getFirstName());
        userModel.setLastName(signupRequestdto.getLastName());

        userModel.setPassword(passwordEncoder.encode(signupRequestdto.getPassword()));
        userModel.setUser_expense(signupRequestdto.getUser_expense());

        UserModel savedUser =  userRepository.save(userModel);

        SignupResponsedto signupResponsedto = new SignupResponsedto();
        signupResponsedto.setUserId(userModel.getId());
        signupResponsedto.setEmailAddress(savedUser.getEmailAddress());
        signupResponsedto.setFirstName(savedUser.getFirstName());
        signupResponsedto.setLastName(savedUser.getLastName());
        System.out.println("Expense"+" "+savedUser.getUser_expense());
        signupResponsedto.setUserExpense(savedUser.getUser_expense());

        return signupResponsedto;
    }

    @Override
    public LoginResponsedto signInuser(LoginRequestdto loginRequestdto) {
        return userRepository.findByEmailAddressAndPassword(loginRequestdto.getEmail(),loginRequestdto.getPassword());
    }

    @Override
    public List<UserDetailResponseDTO> userDetail() {
        List<UserModel> userModel =  userRepository.findAll();
        List<UserDetailResponseDTO> userDetailDTOList = new ArrayList<>();

        for (UserModel userModel1:userModel){
            UserDetailResponseDTO userDetailDTO = new UserDetailResponseDTO();
            userDetailDTO.setId(userModel1.getId());
            userDetailDTOList.add(userDetailDTO);
        }

        return userDetailDTOList;
    }

    @Override
    public EmailResponsedto findById(Integer id) {
        UserModel userModel = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        EmailResponsedto emailResponsedto = new EmailResponsedto();
        emailResponsedto.setEmailAddress(userModel.getEmailAddress());

        return emailResponsedto;
    }
}
