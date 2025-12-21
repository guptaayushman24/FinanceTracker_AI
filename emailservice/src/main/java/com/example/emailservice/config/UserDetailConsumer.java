package com.example.emailservice.config;

import com.example.emailservice.dto.UserDetailResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserDetailConsumer {
    @KafkaListener(
            topics = "t.user.details",
            groupId = "user-details-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeUserDetails(UserDetailResponse dto) {

    }
}






