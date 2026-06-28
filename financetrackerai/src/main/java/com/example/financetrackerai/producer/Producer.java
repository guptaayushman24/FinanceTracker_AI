package com.example.financetrackerai.producer;

import com.example.financetrackerai.dto.SignupResponsedto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    PubSubTemplate pubSubTemplate;

    public void sendUserDetails(SignupResponsedto dto) throws JsonProcessingException {
        pubSubTemplate.publish("t-user-details", objectMapper.writeValueAsString(dto));
    }
}