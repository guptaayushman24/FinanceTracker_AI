package com.example.emailservice.controller;

import com.example.emailservice.config.UserDetail;
import com.example.emailservice.dto.UserDetailResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailTest {
    @Autowired
    UserDetail userDetail;
    @GetMapping("/emailtest")
    public String testEmail() {
        return "Email Sent";
    }

    @GetMapping("/userdetail")
    public ResponseEntity<UserDetailResponse> userDetail(){
      UserDetailResponse userDetailResponse = new UserDetailResponse();
        userDetailResponse.setEmailAddress(userDetail.getEmailAddress());
        userDetailResponse.setFirstName(userDetail.getFirstName());
        userDetailResponse.setLastName(userDetail.getLastName());
        userDetailResponse.setUserExpenses(userDetail.getUserExpenses());

       return ResponseEntity.ok(userDetailResponse);
    }
}
