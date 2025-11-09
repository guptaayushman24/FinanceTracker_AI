package com.example.financetrackerai.controller;

import com.example.financetrackerai.config.StoreUserId;
import com.example.financetrackerai.dto.LoginRequestdto;
import com.example.financetrackerai.dto.LoginResponsedto;
import com.example.financetrackerai.producer.Producer;
import com.example.financetrackerai.security.AuthService;
import com.example.financetrackerai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SignInController {
//    @Autowired
//    UserService userService;
    @Autowired
    AuthService authService;
    @Autowired
    StoreUserId storeUserId;
    @Autowired
    Producer producer;
    @PostMapping("/signin")
    public ResponseEntity<LoginResponsedto> sign (@RequestBody LoginRequestdto loginRequestdto){
        try{
            ResponseEntity<LoginResponsedto> response = authService.login(loginRequestdto);
            int status = response.getStatusCode().value();
//            System.out.println("Login Response is"+" "+loginRequestdto.getEmail());

            if (status==200){
                int userId = response.getBody().getId();
                storeUserId.setUserId(userId);
                producer.sendMessage(userId);
                return authService.login(loginRequestdto);
                // Extract the userId here
            }
            return null;
        }
        catch(Exception e){
            LoginResponsedto loginResponsedto = new LoginResponsedto();
            loginRequestdto.setEmail("null");
            loginRequestdto.setPassword("null");
            loginResponsedto.setJwt("null");
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginResponsedto);
        }
    }
}
