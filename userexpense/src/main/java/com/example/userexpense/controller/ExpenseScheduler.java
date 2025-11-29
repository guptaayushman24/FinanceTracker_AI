package com.example.userexpense.controller;

import com.example.userexpense.dto.ExpenseSchedulerRequestdto;
import com.example.userexpense.dto.ExpenseSchedulerResponsedto;
import com.example.userexpense.service.ExpenseSchedulerService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExpenseScheduler {
    @Autowired
    ExpenseSchedulerService expenseSchedulerService;
    @PostMapping("/scheduling")
    public ResponseEntity<ExpenseSchedulerResponsedto> expenseScheduler(@RequestBody ExpenseSchedulerRequestdto expenseSchedulerRequestdto){
        try{
            return ResponseEntity.ok(expenseSchedulerService.expenseScheduler(expenseSchedulerRequestdto));
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
