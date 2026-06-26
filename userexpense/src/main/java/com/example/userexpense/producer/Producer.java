package com.example.userexpense.producer;

import com.example.userexpense.dto.ExpenseDetailSchedulerdto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendUserPaymentDetail (ExpenseDetailSchedulerdto expenseDetailSchedulerdto) throws  JsonProcessingException {
        kafkaTemplate.send("t.user.expesne.details",objectMapper.writeValueAsString(expenseDetailSchedulerdto));
    }
}
