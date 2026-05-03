package com.example.userexpense.repository.postgres;

import com.example.userexpense.model.PaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentModePostgresRepo extends JpaRepository<PaymentMode, Integer> {
}