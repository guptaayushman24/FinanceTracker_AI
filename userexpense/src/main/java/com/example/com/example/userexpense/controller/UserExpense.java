package com.example.userexpense.controller;

import com.example.userexpense.config.KafkaConsumer;
import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.*;
import com.example.userexpense.exception.HandleEmptyDataException;
import com.example.userexpense.exception.HandleExpenseExceptionByMonth;
import com.example.userexpense.service.UserExpenseService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
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
        return ResponseEntity.ok(userExpenseService.userExpense(userExpenseRequestdto));

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
        return ResponseEntity.ok(userExpenseService.deleteUserExpense(deleteUserExpenseRequestdto));
    }

    @PostMapping("/sortexpense")
    public ResponseEntity<List<SortExpenseResposedto>> sortExpense (@RequestBody SortExpenseRequestdto sortExpenseRequestdto){
        return ResponseEntity.ok(userExpenseService.sortExpense(sortExpenseRequestdto));
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

    @PostMapping("/userexpensebymonth")
    public ResponseEntity<List<AllExpenseeResponsedto>> userExpensebyMonth(@RequestBody MonthNamedto monthName){
        HashMap<String,Integer> monthNameandNumber = new HashMap<>();
        String[] monthList = {
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        };
        for (int i=0;i<monthList.length;i++){
            monthNameandNumber.put(monthList[i],i+1);
        }
        List<String> monthNameList = Arrays.asList(monthList);
        if (!monthNameList.contains(monthName.getMonthName())){
            throw new HandleExpenseExceptionByMonth("Please select the valid month");
        }
        List<AllExpenseeResponsedto> list = userExpenseService.allExpensebyMonth(monthNameandNumber.get(monthName.getMonthName()),monthList);
        if (list.isEmpty()){
            throw new HandleEmptyDataException("You have not recorded any expense in"+" "+monthName.getMonthName());
        }
        return ResponseEntity.ok(list);
    }

        @PostMapping("/indivisualexpense")
    public ResponseEntity<String> indivisualExpense (@RequestBody IndivisualExpenseRequestdto indivisualExpenseRequestdto){
            ResponseEntity.ok(userExpenseService.indivisualUserExpense(indivisualExpenseRequestdto.getExpenseType()));
            return ResponseEntity.ok("Total Expense on"+" "+indivisualExpenseRequestdto.getExpenseType()+" "+"is "+userExpenseService.indivisualUserExpense(indivisualExpenseRequestdto.getExpenseType()).getSum());
    }
}
