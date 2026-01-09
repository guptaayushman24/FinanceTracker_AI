package com.example.com.example.userexpense.service;

import com.example.userexpense.dto.PaymentModeFilterRequestdto;
import com.example.userexpense.dto.PaymentModeFilterResponsedto;
import com.example.userexpense.dto.UserExpenseRequestdto;
import com.example.userexpense.dto.UserExpenseResponsedto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentModeFilterService{
    List<PaymentModeFilterResponsedto> paymentModeFilter (PaymentModeFilterRequestdto paymentModeFilterRequestdto,Integer userId);
}
