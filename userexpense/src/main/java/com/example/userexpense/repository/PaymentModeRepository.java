package com.example.userexpense.repository;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.*;
import com.example.userexpense.model.PaymentMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface PaymentModeRepository extends JpaRepository<PaymentMode,Integer> {
    PaymentModeRequestdto save(PaymentModeRequestdto paymentModeRequestdto);

      void save(String paymentMode);

    @Query("""
    SELECT new com.example.userexpense.dto.PaymentModeFilterResponsedto(
        ue.ExpenseType,
        ue.Value,
        ue.Description,
        pm.paymentMode,
        pm.expenseDate
    )
    FROM UserExpense ue
    JOIN PaymentMode pm ON ue.user_id = pm.user_id
    AND ue.expenseDate = pm.expenseDate
    WHERE (pm.paymentMode = :paymentMode and ue.user_id = :userId)
""")

    List<PaymentModeFilterResponsedto> filterByPaymentMode(@Param("paymentMode") String paymentMode,@Param("userId") Integer userId);
    @Query("""
       SELECT new com.example.userexpense.dto.TotalExpenseMonthResponsedto(
           SUM(ue.Value)
       )
       FROM UserExpense ue
       WHERE ue.user_id = :user_id
         AND MONTHNAME(ue.expenseDate) = :monthName
       """)
    TotalExpenseMonthResponsedto totalExpenseMonthResponsedto(@Param("user_id") Integer user_id,@Param("monthName") String monthName);

    @Query("""
       SELECT new com.example.userexpense.dto.TotalExpenseYearResponsedto(
           SUM(ue.Value)
       )
       FROM UserExpense ue
       WHERE ue.user_id = :user_id
         AND YEAR(ue.expenseDate) = :year
       """)
    TotalExpenseYearResponsedto totalExpenseYearesponsedto(@Param("user_id") Integer user_id, @Param("year") String monthName);

    @Query("""
       SELECT new com.example.userexpense.dto.TotalExpenseCurrentDayResponsedto(
           SUM(ue.Value)
       )
       FROM UserExpense ue
       WHERE ue.user_id = :user_id
         AND ue.expenseDate = :currentDate
       """)
    TotalExpenseCurrentDayResponsedto totalExpenseCurrentResponsedto(@Param("user_id") Integer user_id, @Param("currentDate") LocalDate currentDate);




}
