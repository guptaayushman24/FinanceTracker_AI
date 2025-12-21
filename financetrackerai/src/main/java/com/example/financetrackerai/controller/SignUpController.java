package com.example.financetrackerai.controller;

import com.example.financetrackerai.dto.LoginRequestdto;
import com.example.financetrackerai.dto.LoginResponsedto;
import com.example.financetrackerai.dto.SignupRequestdto;
import com.example.financetrackerai.dto.SignupResponsedto;
import com.example.financetrackerai.producer.Producer;
import com.example.financetrackerai.repository.UserRepository;
import com.example.financetrackerai.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SignUpController {
@Autowired
 UserService userService;
    @Autowired
    Producer producer;
    @PostMapping("/signup")
    public ResponseEntity<SignupResponsedto> signUp(@RequestBody  SignupRequestdto signupRequestdto) {
        try {
           SignupResponsedto signupResponsedto = userService.createUser(signupRequestdto);
           // Create one check here that if user already exist then do not add in database give message do signin
            if (signupResponsedto.getEmailAddress()!=null && signupResponsedto.getFirstName()!=null
                && signupResponsedto.getLastName()!=null && signupResponsedto.getUserExpense()!=null){

                // Produce the Email Address,Expense List,First Name and Last Name in the kafka producer
               producer.sendUserDetails(signupResponsedto);

            }
            return ResponseEntity.ok(signupResponsedto);

        } catch (Exception e) {
            SignupResponsedto signupResponsedto = new SignupResponsedto();
            signupResponsedto.setFirstName(null);
            signupResponsedto.setLastName(null);
            signupResponsedto.setEmailAddress(null);

           e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(signupResponsedto);
        }
    }
}