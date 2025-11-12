package com.example.userexpense.serviceImpl;

import com.example.userexpense.dto.PaymentModeFilterRequestdto;
import com.example.userexpense.dto.PaymentModeFilterResponsedto;
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
    public List<PaymentModeFilterResponsedto> paymentModeFilter(PaymentModeFilterRequestdto paymentModeFilterRequestdto) {
        return paymentModeRepository.filterByPaymentMode(paymentModeFilterRequestdto.getPaymentMode());

    }
}
