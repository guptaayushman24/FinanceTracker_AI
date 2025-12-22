package com.example.userexpense.config;

import com.example.userexpense.dto.KafkaConsumerdto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @Autowired
    UserLoginId userLoginId;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "t.user.id",
            groupId = "user-group",
          containerFactory = "kafkaListenerContainerFactory")

    public void consume(String message){
        try {
            userLoginId.setUserId(Integer.valueOf(message));
            System.out.println("Message is"+" "+message);
        } catch (Exception e) {
            e.printStackTrace(); // NEVER let Kafka crash silently
        }
    }

}