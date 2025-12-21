package com.example.userexpense.controller;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.*;
import com.example.userexpense.service.TotalExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class TotalExpenditure {
    @Autowired
    TotalExpenditureService totalExpenditureService;
    @Autowired
    UserLoginId userLoginId;
    @PostMapping("/totalexpensebymonth")
    public ResponseEntity<TotalExpenseMonthResponsedto> totalExpenseByMonth(@RequestBody TotalExpenseMonthRequestdto totalExpenseMonthRequestdto){
        try{
            return ResponseEntity.ok(totalExpenditureService.totalExpenseMonthResponsedto(userLoginId.getUserId(),totalExpenseMonthRequestdto));
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/totalexpensebyyear")
    public ResponseEntity<TotalExpenseYearResponsedto> totalExpenseByYear(@RequestBody TotalExpenseYearRequestdto totalExpenseYearRequestdto){
        try{
            return ResponseEntity.ok(totalExpenditureService.totalExpenseYearResponsedto(userLoginId.getUserId(),totalExpenseYearRequestdto));
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/totalexpennseoncurrentdate")
    public ResponseEntity<TotalExpenseCurrentDayResponsedto> totalExpenseOnCurrentDate(){
        try{
            LocalDate localDate = LocalDate.now();
            return ResponseEntity.ok(totalExpenditureService.totalExpenseCurrentDayResponsedto(userLoginId.getUserId(),localDate));
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/totalexpensebyyearpaymentmode")
    public ResponseEntity<TotalExpenseYearPaymentModeResponsedto> totalExpenseByYearPaymentMode(@RequestBody TotalExpenseYearPaymentModeRequestdto totalExpenseYearPaymentModeRequestdto){
        try{
            System.out.println("User id is"+" "+userLoginId.getUserId());
            return ResponseEntity.ok(totalExpenditureService.totalExpenseYearPaymentModeResponsedto(userLoginId.getUserId(),totalExpenseYearPaymentModeRequestdto));
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/totalexpensebymonthpaymentmode")
    public ResponseEntity<TotalExpenseMonthPaymentModeResponsedto> totalExpenseByYearPaymentMode(@RequestBody TotalExpenseMonthPaymentModeRequestdto totalExpenseMonthPaymentModeRequestdto){
        try{
            System.out.println("User id is"+" "+userLoginId.getUserId());
            return ResponseEntity.ok(totalExpenditureService.totalExpenseMonthPaymentModeResponsedto(userLoginId.getUserId(),totalExpenseMonthPaymentModeRequestdto));
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/totalexpennseoncurrentdatepaymentmode")
    public ResponseEntity<TotalExpenseCurrentDayPaymentModeResponsedto> totalExpenseOnCurrentDate(@RequestBody TotalExpenseCurrentDayPaymentModeRequestdto totalExpenseCurrentDayPaymentModeRequestdto){
        try{
            LocalDate localDate = LocalDate.now();
            return ResponseEntity.ok(totalExpenditureService.totalExpenseCurrentDayPaymentModeResponsedto(userLoginId.getUserId(),localDate,totalExpenseCurrentDayPaymentModeRequestdto.getPaymentMode()));
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
