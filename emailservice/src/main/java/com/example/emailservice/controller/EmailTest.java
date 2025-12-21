package com.example.emailservice.controller;

import com.example.emailservice.config.UserDetail;
import com.example.emailservice.dto.UserDetailResponse;
import com.example.emailservice.service.EmailService;
import com.example.emailservice.service.EmailTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
public class EmailTest {
    @Autowired
    UserDetailResponse userDetailResponse;
    @Autowired
    EmailService emailService;
    @PostMapping("/sendemail")
    public  String sendEmail (UserDetailResponse userDetailResponse){
        String firstName = userDetailResponse.getFirstName();
        String lastName = userDetailResponse.getLastName();
        String userEmail = userDetailResponse.getEmailAddress();
        List<String> userExpense = userDetailResponse.getUserExpense();
        emailService.sendMail(firstName,lastName,userEmail,userExpense);
        return "Email Send";
    }
}
