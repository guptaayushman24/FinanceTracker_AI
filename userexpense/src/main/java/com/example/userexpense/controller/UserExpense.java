package com.example.userexpense.controller;

import com.example.userexpense.config.KafkaConsumer;
import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.*;
import com.example.userexpense.service.UserExpenseService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

import java.util.List;


@Controller
public class UserExpense {
    @Autowired
    UserExpenseService userExpenseService;
    @Autowired
    KafkaConsumer kafkaConsumer;
    @Autowired
    UserLoginId userLoginId;
    @PostMapping("/userexpense")
    public ResponseEntity<UserExpenseResponsedto> userExpense(@RequestBody UserExpenseRequestdto userExpenseRequestdto){
        try{
            return ResponseEntity.ok(userExpenseService.userExpense(userExpenseRequestdto));
        }
        catch(Exception e){
            UserExpenseResponsedto userExpenseResponsedto = new UserExpenseResponsedto();
            userExpenseResponsedto.setExpenseType(null);
            userExpenseResponsedto.setValue(null);
            userExpenseResponsedto.setDescription(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userExpenseResponsedto);
        }

    }

    @PostMapping("/addnewexpense")
    public ResponseEntity<AddUserExpenseResponsedto> newUserExpense (@RequestBody AddUserExpenseRequestdto addUserExpenseRequestdto){
        try{
            return ResponseEntity.ok(userExpenseService.addUserExpense(addUserExpenseRequestdto));
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/deleteexpense")
    public ResponseEntity<DeleteUserExpenseResponsedto> deleteUserExpense (@RequestBody DeleteUserExpenseRequestdto deleteUserExpenseRequestdto){
        try{
            return ResponseEntity.ok(userExpenseService.deleteUserExpense(deleteUserExpenseRequestdto));
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/sortexpense")
    public ResponseEntity<List<SortExpenseResposedto>> sortExpense (@RequestBody SortExpenseRequestdto sortExpenseRequestdto){
        try{
            return ResponseEntity.ok(userExpenseService.sortExpense(sortExpenseRequestdto));
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/allexpense/{userId}")
    public ResponseEntity<List<AllExpenseeResponsedto>> allUserExpense(@PathVariable Integer userId){
        try{
            List<AllExpenseeResponsedto> allExpense = userExpenseService.allExpense(userId);
            return ResponseEntity.ok(allExpense);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
