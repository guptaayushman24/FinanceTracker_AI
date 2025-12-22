package com.example.financetrackerai.producer;

import com.example.financetrackerai.dto.SignupResponsedto;
import com.example.financetrackerai.model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class Producer {
    @Value("${topic.name}")
    private String userIdTopic;
    private String userId;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
     KafkaTemplate<String,String> kafkaTemplate;

    public String sendMessage (String userId) throws JsonProcessingException{
        kafkaTemplate.send("t.user.id",String.valueOf(userId));
        return "UserId Sent";
    }

    public void sendUserDetails(SignupResponsedto dto)
            throws JsonProcessingException {

        kafkaTemplate.send(
                "t.user.details",
                objectMapper.writeValueAsString(dto)
        );

        System.out.println("Produced USER_DETAILS"); // VALUES are comming
    }




}
