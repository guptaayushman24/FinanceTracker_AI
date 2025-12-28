package com.example.userexpense.serviceImpl;

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

    @Override
    public List<PaymentModeFilterResponsedto> paymentModeFilter(PaymentModeFilterRequestdto paymentModeFilterRequestdto,Integer userId) {
        if (!paymentModeFilterRequestdto.getPaymentMode().equals("UPI") && !paymentModeFilterRequestdto.getPaymentMode().equals("CASH")){
            throw new HandlePaymentModeException("Please select UPI or CASH");
        }
        return paymentModeRepository.filterByPaymentMode(paymentModeFilterRequestdto.getPaymentMode(),userId);

    }
}
