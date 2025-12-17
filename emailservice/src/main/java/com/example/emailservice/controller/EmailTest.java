package com.example.emailservice.controller;

import com.example.emailservice.config.UserDetail;
import com.example.emailservice.dto.UserDetailResponse;
import com.example.emailservice.service.EmailTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RestController
public class EmailTest {
    @Autowired
    UserDetail userDetail;
    @Autowired
    EmailTestService emailTestService;
    @Autowired
    ObjectMapper objectMapper;
    @GetMapping("/emailtest")
    public String testEmail() {
        return "Email Sent";
    }

    @GetMapping("/userdetail")

    public ResponseEntity<UserDetailResponse> userDetail(String message){
        try{
            JsonNode root = objectMapper.readTree(message);
            JsonNode dataNode = root.get("data");

            UserDetailResponse userDetailResponse = objectMapper.treeToValue(dataNode,UserDetailResponse.class);


            return ResponseEntity.ok(userDetailResponse);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception"+ " "+ "::::::"+e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
