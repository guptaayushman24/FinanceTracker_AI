package com.example.userexpense.controller;

import com.example.userexpense.dto.PaymentModeFilterRequestdto;
import com.example.userexpense.dto.PaymentModeFilterResponsedto;
import com.example.userexpense.security.ExtractUserId;
import com.example.userexpense.service.PaymentModeFilterService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentModeFilterController {
    @Autowired
    PaymentModeFilterService paymentModeFilterService;
    @Autowired
    ExtractUserId extractUserId;
    @PostMapping("/filterbypaymentmode")
    public ResponseEntity<List<PaymentModeFilterResponsedto>> paymentModeFilterResponsedtoResponseEntity(@RequestBody PaymentModeFilterRequestdto paymentModeFilterRequestdto, @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
        return ResponseEntity.ok().body(paymentModeFilterService.paymentModeFilter(paymentModeFilterRequestdto,userId));
        }
    }
