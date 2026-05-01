package com.example.financetrackerai.producer;

import com.example.financetrackerai.dto.SignupResponsedto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendUserDetails(SignupResponsedto dto) throws JsonProcessingException {
        kafkaTemplate.send("t.user.details", objectMapper.writeValueAsString(dto));
    }
}
