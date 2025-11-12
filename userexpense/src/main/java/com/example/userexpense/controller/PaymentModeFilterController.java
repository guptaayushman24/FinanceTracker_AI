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

@RestController
public class PaymentModeFilterController {
    @Autowired
    PaymentModeFilterService paymentModeFilterService;
    @PostMapping("/filterbypaymentmode")
    public ResponseEntity<PaymentModeFilterResponsedto> paymentModeFilterResponsedtoResponseEntity(@RequestBody  PaymentModeFilterRequestdto paymentModeFilterRequestdto){
        try{
            return ResponseEntity.ok().body(paymentModeFilterService.paymentModeFilter(paymentModeFilterRequestdto));
        }
        catch(Exception e){
            PaymentModeFilterResponsedto paymentModeFilterResponsedto = new PaymentModeFilterResponsedto();
            paymentModeFilterResponsedto.setExpenseType(null);
            paymentModeFilterResponsedto.setValue(null);
            paymentModeFilterResponsedto.setDescription(null);
            paymentModeFilterResponsedto.setPaymentMode(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(paymentModeFilterResponsedto);
        }
    }
}
