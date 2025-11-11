package com.example.userexpense.repository;

import com.example.userexpense.dto.PaymentModeRequestdto;
import com.example.userexpense.model.PaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentModeRepository extends JpaRepository<PaymentMode,Integer> {
    PaymentModeRequestdto save(PaymentModeRequestdto paymentModeRequestdto);

      void save(String paymentMode);

//    void save(Integer userId, String paymentMode);
}
