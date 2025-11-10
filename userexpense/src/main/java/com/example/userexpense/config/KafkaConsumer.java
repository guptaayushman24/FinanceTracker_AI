package com.example.userexpense.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @Autowired
    UserLoginId userLoginId;
    @KafkaListener(topics = "t.user.id",
                    groupId = "user-group",
                    containerFactory = "kafkaListenerContainerFactory")

    public void consume(String userId){
        userLoginId.setUserId(Integer.valueOf(userId));
        System.out.println("Consumed Message"+" "+userId);
    }
}
