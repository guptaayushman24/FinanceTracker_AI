package com.example.userexpense.controller;

import com.example.userexpense.dto.ExpenseAnalyzerRequestdto;
import com.example.userexpense.dto.ExpenseAnalyzerResponsedto;
import com.example.userexpense.security.ExtractUserId;
import com.example.userexpense.service.ExpenseAnalyzerService;
import com.example.userexpense.serviceImpl.ExpenseAnalyzerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ExpenseAnalyzer {
    @Autowired
    ExtractUserId extractUserId;
    @Autowired
    ExpenseAnalyzerService expenseAnalyzerService;
    @PostMapping("/expenseanalyzer")
    public ResponseEntity<String> expenseAnalyzerdtoResponse (@RequestBody ExpenseAnalyzerRequestdto expenseAnalyzerRequestdto, @RequestHeader("Authorization") String authorizationHeader) throws JsonProcessingException {
        try{
            String token = authorizationHeader.substring(7);
            int userId = extractUserId.getUserIdFromToken(token).intValue();
            return ResponseEntity.ok(expenseAnalyzerService.analyzeExpense(userId,expenseAnalyzerRequestdto.getYear()));
        }
        catch(Exception e) {
            System.out.println("Exception occur ::::::" + " " + e);
             e.printStackTrace();
        }
        return null;
    }
}
