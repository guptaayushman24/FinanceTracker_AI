package com.example.emailservice.config;

import com.example.emailservice.dto.UserDetailResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserDetailConsumer {
    @KafkaListener(
            topics = "t.user.details",
            groupId = "user-details-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeUserDetails(UserDetailResponse dto) {

        System.out.println("Received User Details:");
        System.out.println(dto.getEmailAddress());
        System.out.println(dto.getFirstName());
        System.out.println(dto.getLastName());
    }
}






