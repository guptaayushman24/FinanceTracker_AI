package com.example.financetrackerai.producer;

import com.example.financetrackerai.model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Producer {
    @Value("${topic.name}")
    private String userIdTopic;
    private String userId;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
     KafkaTemplate<String,Integer> kafkaTemplate;

    public String sendMessage (Integer userId) throws JsonProcessingException{
//        String userIdAsMesage = objectMapper.writeValueAsString(userModel);
        kafkaTemplate.send(userIdTopic,userId);

        log.info("User id is produced {}",userId);
        return "Message Sent";
    }




}
