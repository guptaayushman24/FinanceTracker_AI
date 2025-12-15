package com.example.emailservice.config;

import com.example.emailservice.dto.UserDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class KafkaConsumer {
    @Autowired
    private UserDetail userDetail;
    @Autowired
    private ObjectMapper objectMapper;
    @KafkaListener(topics = "t.user.id",
            groupId = "user-group",
            containerFactory = "kafkaListenerContainerFactory")

    public void consumeUserDetail(String message){
        JsonNode root = objectMapper.readTree(message);
        String type = root.get("type").asText();
        if (("USER_DETAILS").equals(type)) {
            System.out.println("Hello");
            UserDetailResponse userDetailResponse = objectMapper.treeToValue(root.get("data"), UserDetailResponse.class);
            System.out.println("User Detail"+" "+userDetailResponse);
            userDetail.setEmailAddress(userDetailResponse.getEmailAddress());
            userDetail.setFirstName(userDetailResponse.getFirstName());
            userDetail.setLastName(userDetailResponse.getLastName());
            userDetail.setUserExpenses(userDetailResponse.getUserExpenses());
        }

    }
}
