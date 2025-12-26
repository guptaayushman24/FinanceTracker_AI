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
        return ResponseEntity.ok(totalExpenditureService.totalExpenseMonthResponsedto(userLoginId.getUserId(),totalExpenseMonthRequestdto));
    }


    @PostMapping("/totalexpensebyyear")
    public ResponseEntity<TotalExpenseYearResponsedto> totalExpenseByYear(@RequestBody TotalExpenseYearRequestdto totalExpenseYearRequestdto){
        return ResponseEntity.ok(totalExpenditureService.totalExpenseYearResponsedto(userLoginId.getUserId(),totalExpenseYearRequestdto));
    }

    @GetMapping("/totalexpennseoncurrentdate")
    public ResponseEntity<TotalExpenseCurrentDayResponsedto> totalExpenseOnCurrentDate(){
        LocalDate localDate = LocalDate.now();
        return ResponseEntity.ok(totalExpenditureService.totalExpenseCurrentDayResponsedto(userLoginId.getUserId(),localDate));
    }

    @PostMapping("/totalexpensebyyearpaymentmode")
    public ResponseEntity<TotalExpenseYearPaymentModeResponsedto> totalExpenseByYearPaymentMode(@RequestBody TotalExpenseYearPaymentModeRequestdto totalExpenseYearPaymentModeRequestdto){
        return ResponseEntity.ok(totalExpenditureService.totalExpenseYearPaymentModeResponsedto(userLoginId.getUserId(),totalExpenseYearPaymentModeRequestdto));
    }

    @PostMapping("/totalexpensebymonthpaymentmode")
    public ResponseEntity<TotalExpenseMonthPaymentModeResponsedto> totalExpenseByYearPaymentMode(@RequestBody TotalExpenseMonthPaymentModeRequestdto totalExpenseMonthPaymentModeRequestdto){
        return ResponseEntity.ok(totalExpenditureService.totalExpenseMonthPaymentModeResponsedto(userLoginId.getUserId(),totalExpenseMonthPaymentModeRequestdto));
    }

    @PostMapping("/totalexpennseoncurrentdatepaymentmode")
    public ResponseEntity<TotalExpenseCurrentDayPaymentModeResponsedto> totalExpenseOnCurrentDate(@RequestBody TotalExpenseCurrentDayPaymentModeRequestdto totalExpenseCurrentDayPaymentModeRequestdto){
        LocalDate localDate = LocalDate.now();
        return ResponseEntity.ok(totalExpenditureService.totalExpenseCurrentDayPaymentModeResponsedto(userLoginId.getUserId(),localDate,totalExpenseCurrentDayPaymentModeRequestdto.getPaymentMode()));
    }

}
