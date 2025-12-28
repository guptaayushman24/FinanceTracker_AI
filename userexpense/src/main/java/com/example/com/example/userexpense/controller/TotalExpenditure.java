package com.example.userexpense.controller;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.*;
import com.example.userexpense.security.ExtractUserId;
import com.example.userexpense.service.TotalExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class TotalExpenditure {
    @Autowired
    TotalExpenditureService totalExpenditureService;
    @Autowired
    UserLoginId userLoginId;
    @Autowired
    ExtractUserId extractUserId;
    @PostMapping("/totalexpensebymonth")
    public ResponseEntity<TotalExpenseMonthResponsedto> totalExpenseByMonth(@RequestBody TotalExpenseMonthRequestdto totalExpenseMonthRequestdto ,@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
        return ResponseEntity.ok(totalExpenditureService.totalExpenseMonthResponsedto(userId,totalExpenseMonthRequestdto));
    }


    @PostMapping("/totalexpensebyyear")
    public ResponseEntity<TotalExpenseYearResponsedto> totalExpenseByYear(@RequestBody TotalExpenseYearRequestdto totalExpenseYearRequestdto,@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
        return ResponseEntity.ok(totalExpenditureService.totalExpenseYearResponsedto(userId,totalExpenseYearRequestdto));
    }

    @GetMapping("/totalexpennseoncurrentdate")
    public ResponseEntity<TotalExpenseCurrentDayResponsedto> totalExpenseOnCurrentDate(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
        LocalDate localDate = LocalDate.now();
        return ResponseEntity.ok(totalExpenditureService.totalExpenseCurrentDayResponsedto(userId,localDate));
    }

    @PostMapping("/totalexpensebyyearpaymentmode")
    public ResponseEntity<TotalExpenseYearPaymentModeResponsedto> totalExpenseByYearPaymentMode(@RequestBody TotalExpenseYearPaymentModeRequestdto totalExpenseYearPaymentModeRequestdto,@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
        return ResponseEntity.ok(totalExpenditureService.totalExpenseYearPaymentModeResponsedto(userId,totalExpenseYearPaymentModeRequestdto));
    }

    @PostMapping("/totalexpensebymonthpaymentmode")
    public ResponseEntity<TotalExpenseMonthPaymentModeResponsedto> totalExpenseByYearPaymentMode(@RequestBody TotalExpenseMonthPaymentModeRequestdto totalExpenseMonthPaymentModeRequestdto,@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
        return ResponseEntity.ok(totalExpenditureService.totalExpenseMonthPaymentModeResponsedto(userId,totalExpenseMonthPaymentModeRequestdto));
    }

    @PostMapping("/totalexpennseoncurrentdatepaymentmode")
    public ResponseEntity<TotalExpenseCurrentDayPaymentModeResponsedto> totalExpenseOnCurrentDate(@RequestBody TotalExpenseCurrentDayPaymentModeRequestdto totalExpenseCurrentDayPaymentModeRequestdto,@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
        LocalDate localDate = LocalDate.now();
        return ResponseEntity.ok(totalExpenditureService.totalExpenseCurrentDayPaymentModeResponsedto(userId,localDate,totalExpenseCurrentDayPaymentModeRequestdto.getPaymentMode()));
    }

}
