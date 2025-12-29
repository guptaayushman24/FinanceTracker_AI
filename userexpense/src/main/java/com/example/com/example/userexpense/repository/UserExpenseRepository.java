package com.example.userexpense.repository;

import com.example.userexpense.dto.*;
import com.example.userexpense.model.UserExpense;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Repository
public interface UserExpenseRepository extends JpaRepository<UserExpense, Integer> {
    UserExpenseResponsedto save(UserExpenseRequestdto userExpenseRequestdto);

    @Query(value = """
            select user_expense from user_expenses where user_id = :user_id;
            """, nativeQuery = true)
    HashSet<String> checkUserExpenseExist(@Param("user_id") Integer user_id);

    @Modifying
    @Transactional
    @Query(
            value = """
                    INSERT INTO user_expenses (user_id,user_expense) VALUES (:user_id,:expense_type);
                    """
            , nativeQuery = true
    )
    void addNewUserExpense(@Param("user_id") Integer user_id, @Param("expense_type") String expense_type);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM user_expenses WHERE user_id = :user_id AND user_expense = :expense_type",
            nativeQuery = true
    )
    void deleteUserExpense(@Param("user_id") Integer userId,
                           @Param("expense_type") String expenseType);

    @Query("""
            SELECT new com.example.userexpense.dto.SortExpenseResposedto(
                ue.ExpenseType,
                ue.Value,
                ue.Description,
                pm.paymentMode,
                pm.expenseDate
            )
            FROM UserExpense ue
            JOIN PaymentMode pm ON ue.user_id = pm.user_id
            AND ue.expenseDate = pm.expenseDate
            WHERE ue.user_id = :userId""")
    List<SortExpenseResposedto> allUserExpensebyId(@Param("userId") Integer userId);

    @Query("""
            SELECT new com.example.userexpense.dto.AllExpenseeResponsedto(
                ue.ExpenseType,
                ue.Value,
                ue.Description,
                pm.paymentMode,
                pm.expenseDate
            )
            FROM UserExpense ue
            JOIN PaymentMode pm ON ue.user_id = pm.user_id
            AND ue.expenseDate = pm.expenseDate
            WHERE ue.user_id = :userId""")
    List<AllExpenseeResponsedto> allUserExpense(@Param("userId") Integer userId);

    @Query("""
            SELECT new com.example.userexpense.dto.AllExpenseeResponsedto(
                ue.ExpenseType,
                ue.Value,
                ue.Description,
                pm.paymentMode,
                pm.expenseDate
            )
            FROM UserExpense ue
            JOIN PaymentMode pm ON ue.user_id = pm.user_id
            AND ue.expenseDate = pm.expenseDate
            WHERE (ue.user_id =:userId and MONTH(ue.expenseDate)=:monthNumber)""")
    List<AllExpenseeResponsedto> allUserExpenseByMonth(@Param("userId") Integer userId, @Param("monthNumber") Integer monthNumber);


    @Query("""
            SELECT new com.example.userexpense.dto.UserExpensePieChartByMonthdto(
                ue.ExpenseType,
                SUM(ue.Value)
            )
            FROM UserExpense ue
            WHERE ue.user_id = :userId
              AND FUNCTION('MONTHNAME', ue.expenseDate) = :monthName
              AND YEAR(ue.expenseDate) = :year
            GROUP BY ue.ExpenseType
            """)
    List<UserExpensePieChartByMonthdto> userExpensePieChartByMonth(@Param("userId") Integer userId, @Param("monthName") String monthName,@Param("year") Integer year);

    @Query("""
            SELECT new com.example.userexpense.dto.UserExpensePieChartByMonthdto(
                ue.ExpenseType,
                SUM(ue.Value)
            )
            FROM UserExpense ue
            WHERE ue.user_id = :userId
              AND FUNCTION('YEAR', ue.expenseDate) = :year
            GROUP BY ue.ExpenseType
            """)
    List<UserExpensePieChartByMonthdto> userExpensePieChartByYear(@Param("userId") Integer userId, @Param("year") Integer year);

    @Query("""
            SELECT new com.example.userexpense.dto.BarGraphdto(
               pm.paymentMode,
               COUNT(pm.paymentMode)
            )
            FROM PaymentMode pm
            WHERE pm.user_id=:userId
            AND MONTHNAME(pm.expenseDate)=:monthName
            AND YEAR (pm.expenseDate)=:year 
             GROUP BY pm.paymentMode
            """)

    List<BarGraphdto> userExpenseBarGraphByMonth(@Param("userId") Integer userId,@Param("monthName") String monthName,@Param("year") Integer year);

    @Query("""
            SELECT new com.example.userexpense.dto.BarGraphdto(
               pm.paymentMode,
               COUNT(pm.paymentMode)
            )
            FROM PaymentMode pm
            WHERE pm.user_id=:userId
            AND FUNCTION('YEAR',pm.expenseDate)=:monthName GROUP BY pm.paymentMode
            """)

    List<BarGraphdto> userExpenseBarGraphByYear(@Param("userId") Integer userId,@Param("monthName") Integer year);

        @Query("""
        SELECT new com.example.userexpense.dto.IndivisualExpensesqldto(
            ue.ExpenseType,
            SUM(ue.Value)
        )
        FROM UserExpense ue
        WHERE ue.ExpenseType = :expenseType
          AND ue.user_id = :userId
        GROUP BY ue.ExpenseType
    """)
        IndivisualExpensesqldto indivisualExpense (@Param("userId") Integer userId, @Param("expenseType") String expenseType);

        @Query(
                """
                 SELECT new com.example.userexpense.dto.ExpenseExistdto(
                   ue.ExpenseType
                 )
                 FROM UserExpense ue
                 WHERE ue.user_id = :userId
                """
        )
    List<ExpenseExistdto> expenseExist (@Param("userId") Integer userId);
}