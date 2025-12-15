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
//        String userIdAsMesage = objectMapper.writeValueAsString(userModel);
        kafkaTemplate.send(userIdTopic,String.valueOf(userId));
//
//        log.info("User id is produced {}",userId);
//        return "Message Sent";
        Map<String,Object> message = new HashMap<>();
        message.put("type","USER_ID");
        message.put("data",userId);

        kafkaTemplate.send(userIdTopic,objectMapper.writeValueAsString(message));
        return "UserId Sent";
    }

    public String sendUserDetails (SignupResponsedto signupResponsedto) throws JsonProcessingException{
//        kafkaTemplate.send(userIdTopic,String.valueOf(signupResponsedto));
//        log.info("User id is produced {}",userId);
//        return "User Detail Sent";
        Map<String,Object> message = new HashMap<>();
        message.put("type","USER_DETAILS");
        message.put("data",signupResponsedto);

        kafkaTemplate.send(userIdTopic,objectMapper.writeValueAsString(message));

        return "User Details Sent";
    }




}
