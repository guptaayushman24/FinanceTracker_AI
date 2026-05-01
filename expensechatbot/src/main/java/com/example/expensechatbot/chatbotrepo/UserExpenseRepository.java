package com.example.expensechatbot.chatbotrepo;

import com.example.expensechatbot.model.UserExpense;
import com.example.expensechatbot.responsedto.SumOfExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface UserExpenseRepository extends JpaRepository<UserExpense,Integer> {
    @Query("""
        SELECT new com.example.expensechatbot.responsedto.SumOfExpense(
            SUM(ue.Value)
        )
        FROM UserExpense ue
        WHERE MONTH(ue.expenseDate) >= :startMonth
        AND MONTH(ue.expenseDate) <= :endMonth
        AND YEAR(ue.expenseDate) = :year
        AND ue.user_id = :userId
        """)
    SumOfExpense somOfExpenseBetweenTwoMonth(@Param("userId") Integer userId, @Param("startMonth") Integer startMonth, @Param("endMonth") Integer endMonth, @Param("year") Integer year);

    @Query("""
            SELECT new com.example.expensechatbot.responsedto.SumOfExpense(
            SUM(ue.Value)
            )
            FROM UserExpense ue
            WHERE ue.expenseDate BETWEEN :startDate AND :endDate
            AND ue.user_id = :userId
            """)
    SumOfExpense sumOfExpenseBetweenTwoDate(@Param("userId") Integer userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(
            """
                  SELECT new com.example.expensechatbot.responsedto.SumOfExpense(
            SUM(ue.Value)
            )
                    FROM UserExpense ue
                            WHERE ue.user_id = :userId
                            AND ue.expenseDate BETWEEN :startDate AND :endDate
                            AND UPPER(ue.paymentMode.paymentMode) = UPPER(:paymentMode)
            """
    )
    SumOfExpense sumOfExpenseBetweenTwoDateByPaymentMode(@Param("userId") Integer userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,@Param("paymentMode") String paymentMode);

    @Query("""
             SELECT new com.example.expensechatbot.responsedto.SumOfExpense(
            SUM(ue.Value)
            )
            FROM UserExpense ue
              WHERE ue.user_id = :userId
             AND YEAR(ue.expenseDate) = :year
            """)

    SumOfExpense sumOfExpenseBetweenInAyear(@Param("userId") Integer userId, @Param("year") Integer year);

    @Query("""
        SELECT new com.example.expensechatbot.responsedto.SumOfExpense(
            SUM(ue.Value)
        )
        FROM UserExpense ue
        WHERE MONTH(ue.expenseDate) >= :startMonth
        AND MONTH(ue.expenseDate) <= :endMonth
        AND YEAR(ue.expenseDate) = :year
        AND ue.user_id = :userId
        AND UPPER(ue.paymentMode.paymentMode) = UPPER(:paymentMode)
        """)

    SumOfExpense sumOfExpenseBetweenMonthByPaymentMode(@Param("userId") Integer userId, @Param("startMonth") Integer startMonth, @Param("endMonth") Integer endMonth, @Param("year") Integer year,@Param("paymentMode") String paymentMode);

    @Query("""
            SELECT new com.example.expensechatbot.responsedto.SumOfExpense(
            SUM(ue.Value)
        )
         FROM UserExpense ue
        WHERE YEAR(ue.expenseDate) = :year
        AND ue.user_id = :userId
        AND UPPER(ue.paymentMode.paymentMode) = UPPER(:paymentMode)
        """)

    SumOfExpense sumOfExpenseInAYearByPaymentMode(@Param("userId") Integer userId,@Param("year") Integer year,@Param("paymentMode") String paymentMode);


}
