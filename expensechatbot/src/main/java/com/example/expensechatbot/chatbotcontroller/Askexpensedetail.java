package com.example.expensechatbot.chatbotcontroller;

import com.example.expensechatbot.chatbotservice.ChatbotAssistant;
import com.example.expensechatbot.requestdto.RequestDto;
import com.example.expensechatbot.security.ExtractUserId;
import com.example.expensechatbot.security.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/chatbot")
public class Askexpensedetail {

    @Autowired
    private ChatbotAssistant chatbotAssistant;
    @Autowired
    private ExtractUserId extractUserId;
    @Autowired
    private UserContext userContext;

    @PostMapping("/query")
    public ResponseEntity<String> query(@RequestBody RequestDto requestDto, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        userContext.setUserId(extractUserId.getUserIdFromToken(token).intValue());
        int currentYear = LocalDate.now().getYear(); // ← get actual current year
        String currentDate = LocalDate.now().toString();
        String currentMonth = LocalDate.now().getMonth().name();

        String response = chatbotAssistant.chat(requestDto.getQuery(),currentYear,currentDate,currentMonth);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public String test (){
        return "Say Hello";
    }

}