package com.example.userexpense.controller;

import com.example.userexpense.dto.PaymentModeFilterRequestdto;
import com.example.userexpense.dto.PaymentModeFilterResponsedto;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentModeFilterController {
    public ResponseEntity<PaymentModeFilterResponsedto> paymentModeFilterResponsedtoResponseEntity(PaymentModeFilterRequestdto paymentModeFilterRequestdto){
        try{
            // Service class

            return null;
        }
        catch(Exception e){
            PaymentModeFilterResponsedto paymentModeFilterResponsedto = new PaymentModeFilterResponsedto();
            paymentModeFilterResponsedto.setExpenseType(null);
            paymentModeFilterResponsedto.setValue(null);
            paymentModeFilterResponsedto.setDescription(null);
            paymentModeFilterResponsedto.setPayment_mode(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(paymentModeFilterResponsedto);
        }
    }
}
