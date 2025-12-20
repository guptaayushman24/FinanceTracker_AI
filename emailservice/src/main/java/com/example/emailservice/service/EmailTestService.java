package com.example.emailservice.service;


import com.example.emailservice.dto.UserDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class EmailTestService {
        @Autowired
        private ObjectMapper objectMapper;

        @KafkaListener(
                topics = "t.user.details"   ,
                groupId = "user-details"
        )
        public void consumeUserDetails(String message) throws Exception {
            System.out.println("Received message: " + message);
        }
    }
