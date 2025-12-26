package com.example.userexpense.serviceImpl;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.PaymentModeFilterRequestdto;
import com.example.userexpense.dto.PaymentModeFilterResponsedto;
import com.example.userexpense.exception.HandlePaymentModeException;
import com.example.userexpense.repository.PaymentModeRepository;
import com.example.userexpense.service.PaymentModeFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentModeFilterControllerServiceImpl implements PaymentModeFilterService {
    @Autowired
    PaymentModeRepository paymentModeRepository;
    @Autowired
    UserLoginId userLoginId;

    @Override
    public List<PaymentModeFilterResponsedto> paymentModeFilter(PaymentModeFilterRequestdto paymentModeFilterRequestdto) {
        if (!paymentModeFilterRequestdto.getPaymentMode().equals("UPI") && !paymentModeFilterRequestdto.getPaymentMode().equals("CASH")){
            throw new HandlePaymentModeException("Please select UPI or CASH");
        }
        System.out.println("The user Id in "+" "+userLoginId.getUserId());
        return paymentModeRepository.filterByPaymentMode(paymentModeFilterRequestdto.getPaymentMode(),userLoginId.getUserId());

    }
}
