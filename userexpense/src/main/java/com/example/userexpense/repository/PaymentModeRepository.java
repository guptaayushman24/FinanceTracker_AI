package com.example.userexpense.repository;

import com.example.userexpense.dto.PaymentModeFilterResponsedto;
import com.example.userexpense.dto.PaymentModeRequestdto;
import com.example.userexpense.model.PaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentModeRepository extends JpaRepository<PaymentMode,Integer> {
    PaymentModeRequestdto save(PaymentModeRequestdto paymentModeRequestdto);

      void save(String paymentMode);
//    PaymentModeFilterResponsedto findBypaymentMode(String paymentMode);
    @Query(
            value = " select expense_type,description,value,payment_mode,user_expense.expense_date from user_expense JOIN payment_mode ON user_expense.user_id=payment_mode.user_id where payment_mode:=payment_mode"
    )
    List<PaymentModeFilterResponsedto> filterByPaymentMode(@Param("payment_mode") String payment_mode);


}
