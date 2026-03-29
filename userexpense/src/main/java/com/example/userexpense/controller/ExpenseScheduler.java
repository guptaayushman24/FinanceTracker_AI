package com.example.userexpense.controller;

import com.example.userexpense.dto.AllExpenseeResponsedto;
import com.example.userexpense.dto.ExpenseSchedulerRequestdto;
import com.example.userexpense.dto.ExpenseSchedulerResponsedto;
import com.example.userexpense.security.ExtractUserId;
import com.example.userexpense.service.ExpenseSchedulerService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ExpenseScheduler {
    @Autowired
    ExpenseSchedulerService expenseSchedulerService;
    @Autowired
    ExtractUserId extractUserId;
    @PostMapping("/scheduling")
    public ResponseEntity<ExpenseSchedulerResponsedto> expenseScheduler(@RequestBody ExpenseSchedulerRequestdto expenseSchedulerRequestdto,@RequestHeader("Authorization") String authorizationHeader){
        try{
            String token = authorizationHeader.substring(7);
            Integer userId = extractUserId.getUserIdFromToken(token).intValue();
            return ResponseEntity.ok(expenseSchedulerService.expenseScheduler(expenseSchedulerRequestdto,userId));
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

//    @GetMapping("/tiggerscheduler/{userId}")
//    public ResponseEntity<List<AllExpenseeResponsedto>> allUserExpense(@PathVariable("userId") Integer userId){
//        try{
//            LocalDate expenseDate = LocalDate.now();
//            List<AllExpenseeResponsedto> allExpense = expenseSchedulerService.expenseRecordScheduler(userId,expenseDate);
//            return ResponseEntity.ok(allExpense);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

}
