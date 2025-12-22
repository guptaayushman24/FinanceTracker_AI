package com.example.emailservice.service;


import com.example.emailservice.dto.UserDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class EmailTestService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    EmailService emailService;
        @KafkaListener(
            topics = "t.user.details",
            groupId = "user-details-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeUserDetails(UserDetailResponse userDetailResponse) throws Exception {
        // As the kafka get the userDetail automatically trigger the email
        emailService.sendMail(userDetailResponse.getFirstName(), userDetailResponse.getLastName(),
                    userDetailResponse.getEmailAddress(),userDetailResponse.getUserExpense());

    }
}
