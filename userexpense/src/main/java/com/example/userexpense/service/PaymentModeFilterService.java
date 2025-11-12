package com.example.userexpense.service;

import com.example.userexpense.dto.PaymentModeFilterRequestdto;
import com.example.userexpense.dto.PaymentModeFilterResponsedto;
import com.example.userexpense.dto.UserExpenseRequestdto;
import com.example.userexpense.dto.UserExpenseResponsedto;
import org.springframework.stereotype.Service;

@Service
public interface PaymentModeFilterService{
    PaymentModeFilterResponsedto paymentModeFilter (PaymentModeFilterRequestdto paymentModeFilterRequestdto);
}
