package com.example.userexpense.service;

import com.example.userexpense.dto.ExpenseAnalyzerRequestdto;
import com.example.userexpense.dto.ExpenseAnalyzerResponsedto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExpenseAnalyzerService {
    public String analyzeExpense (Integer userId, Integer year) throws JsonProcessingException;
}