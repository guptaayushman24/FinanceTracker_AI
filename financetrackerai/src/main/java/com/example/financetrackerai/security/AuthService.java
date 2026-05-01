package com.example.financetrackerai.security;

import com.example.financetrackerai.dto.LoginRequestdto;
import com.example.financetrackerai.dto.LoginResponsedto;
import com.example.financetrackerai.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthUtil authUtil;

    public ResponseEntity<LoginResponsedto> login(LoginRequestdto loginRequestdto) {
        try {
            if (loginRequestdto.getEmail() == null || loginRequestdto.getPassword() == null) {
                throw new Exception("Fields are missing");
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestdto.getEmail(), loginRequestdto.getPassword())
            );
            UserModel userModel = (UserModel) authentication.getPrincipal();
            String token = authUtil.generationAccessToken(userModel);
            return ResponseEntity.ok(new LoginResponsedto(userModel.getId(), userModel.getEmailAddress(), token));
        } catch (Exception e) {
            System.out.println("Exception in login :::::" + " " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponsedto());
        }
    }
}
