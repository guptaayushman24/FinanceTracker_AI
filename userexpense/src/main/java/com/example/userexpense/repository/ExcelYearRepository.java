package com.example.userexpense.repository;

import com.example.userexpense.dto.UserExpensePaymentMode;
import com.example.userexpense.model.PaymentMode;
import com.example.userexpense.model.UserExpense;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/*public interface ExcelYearRepository extends JpaRepository<UserExpense,Integer> {
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
                                                             @Param("expense_date") Integer expense_date);

}*/


public interface ExcelYearRepository extends JpaRepository<UserExpense,Integer> {
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
      AND (
           YEAR(ue.expenseDate) = :expense_date
      )
"""
    )
    List<UserExpensePaymentMode> earlyExpenseDataToExcel(@Param("user_id") Integer user_id,
                                                         @Param("expense_date") Integer expense_date);


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
                            WHERE ue.user_id = :userId
                              AND MONTHNAME(ue.expenseDate) = :monthName
                              AND YEAR(ue.expenseDate) = :expense_date
"""
    )
    List<UserExpensePaymentMode> monthlyExpenseDataToExcel (@Param("userId") Integer userId
                            ,@Param("monthName") String monthName,@Param("expense_date") Integer expense_date);

}




