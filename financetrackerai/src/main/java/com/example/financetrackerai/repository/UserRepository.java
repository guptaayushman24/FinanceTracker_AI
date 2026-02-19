package com.example.financetrackerai.repository;

import com.example.financetrackerai.dto.LoginResponsedto;
import com.example.financetrackerai.dto.ProfileResponseDto;
import com.example.financetrackerai.dto.SignupRequestdto;
import com.example.financetrackerai.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Integer> {
    UserModel findByemailAddress(String emailAddress);
    SignupRequestdto save(SignupRequestdto signupRequestdto);
    LoginResponsedto findByEmailAddressAndPassword(String email, String password);
}
