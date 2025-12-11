package com.example.userexpense.repository;

import com.example.userexpense.dto.UserExpensePaymentMode;
import com.example.userexpense.model.PaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExcelYearRepository extends JpaRepository<PaymentMode,Integer> {
    @Query(
            """
            SELECT new com.example.userexpense.dto.UserExpensePaymentMode(
                ue.Description, 
                ue.ExpenseType, 
                ue.Value, 
                ue.expenseDate,
                pm.paymentMode
            )
            FROM UserExpense ue
            JOIN ue.paymentMode pm
            WHERE ue.user_id = :user_id
              AND YEAR(ue.expenseDate) = :expense_date
            """
    )
    List<UserExpensePaymentMode> earlyExpenseDataToExcel(@Param("user_id") Integer user_id,
                                                             @Param("expense_date") String expense_date);

}



