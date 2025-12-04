package com.example.userexpense.controller;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.TotalExpenseMonthRequestdto;
import com.example.userexpense.dto.TotalExpenseMonthResponsedto;
import com.example.userexpense.dto.TotalExpenseYearRequestdto;
import com.example.userexpense.dto.TotalExpenseYearResponsedto;
import com.example.userexpense.service.TotalExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
