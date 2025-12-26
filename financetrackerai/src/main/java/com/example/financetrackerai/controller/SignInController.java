package com.example.financetrackerai.controller;

import com.example.financetrackerai.config.StoreUserId;
import com.example.financetrackerai.dto.LoginRequestdto;
import com.example.financetrackerai.dto.LoginResponsedto;
import com.example.financetrackerai.exception.UserNotFoundException;
import com.example.financetrackerai.producer.Producer;
import com.example.financetrackerai.security.AuthService;
import com.example.financetrackerai.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.coyote.Response;
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
            return authService.login(loginRequestdto);
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
