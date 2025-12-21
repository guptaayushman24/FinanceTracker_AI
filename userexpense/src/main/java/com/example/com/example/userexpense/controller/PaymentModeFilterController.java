package com.example.userexpense.controller;

import com.example.userexpense.dto.PaymentModeFilterRequestdto;
import com.example.userexpense.dto.PaymentModeFilterResponsedto;
import com.example.userexpense.service.PaymentModeFilterService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentModeFilterController {
    @Autowired
    PaymentModeFilterService paymentModeFilterService;
    @PostMapping("/filterbypaymentmode")
    public ResponseEntity<List<PaymentModeFilterResponsedto>> paymentModeFilterResponsedtoResponseEntity(@RequestBody  PaymentModeFilterRequestdto paymentModeFilterRequestdto){
        try{
            return ResponseEntity.ok().body(paymentModeFilterService.paymentModeFilter(paymentModeFilterRequestdto));
        }
        catch(Exception e){
            e.printStackTrace();
//            paymentModeFilterResponsedto.get(0).setExpenseType(null);
//            paymentModeFilterResponsedto.get(0).setPaymentMode(null);
//            paymentModeFilterResponsedto.get(0).setDescription(null);
//            paymentModeFilterResponsedto.get(0).setExpense_date(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
