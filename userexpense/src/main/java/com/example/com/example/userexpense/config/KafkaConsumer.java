package com.example.userexpense.config;

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

//    public void consume(String userId){
//        userLoginId.setUserId(Integer.valueOf(userId));
//        System.out.println("Consumed Message"+" "+userId);
//
//
//    }
    public void consume(String message) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(message);
        String type = root.get("type").asText();
        if (("USER_ID").equals(type)) {
            String userId = root.get("data").asText();
            userLoginId.setUserId(Integer.valueOf(userId));
            System.out.println("Received USER_ID: " + userId);
        }
    }
}