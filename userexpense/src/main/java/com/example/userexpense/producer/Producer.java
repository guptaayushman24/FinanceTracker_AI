package com.example.userexpense.producer;

import com.example.userexpense.dto.ExpenseDetailSchedulerdto;
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

    public void sendUserPaymentDetail(ExpenseDetailSchedulerdto expenseDetailSchedulerdto) throws JsonProcessingException {
        pubSubTemplate.publish("t-user-expense-details", objectMapper.writeValueAsString(expenseDetailSchedulerdto));
    }
}