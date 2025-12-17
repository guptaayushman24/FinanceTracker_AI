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
                topics = "t.user.details",
                groupId = "user-group"
        )
        public void consumeUserDetails(String message) throws Exception {

            // 🔹 Step 1: Print raw message
            System.out.println("Raw Kafka Message: " + message);

            // 🔹 Step 2: Convert String → DTO
            UserDetailResponse dto =
                    objectMapper.readValue(message, UserDetailResponse.class);

            // 🔹 Step 3: Use the data
            System.out.println("Email: " + dto.getEmailAddress());
            System.out.println("First Name: " + dto.getFirstName());
            System.out.println("Last Name: " + dto.getLastName());
        }
    }
