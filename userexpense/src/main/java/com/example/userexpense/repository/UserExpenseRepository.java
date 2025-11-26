package com.example.userexpense.repository;

import com.example.userexpense.dto.*;
import com.example.userexpense.model.UserExpense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Repository
public interface UserExpenseRepository extends JpaRepository<UserExpense,Integer> {
    UserExpenseResponsedto save(UserExpenseRequestdto userExpenseRequestdto);
    @Query(value = """
            select user_expense from user_expenses where user_id = :user_id;
            """,nativeQuery = true)

   HashSet<String> checkUserExpenseExist(@Param("user_id") Integer user_id);

    @Modifying
    @Transactional
   @Query(
           value = """
                   INSERT INTO user_expenses (user_id,user_expense) VALUES (:user_id,:expense_type);
                   """
                ,nativeQuery = true
   )
    void addNewUserExpense(@Param("user_id") Integer user_id,@Param("expense_type") String expense_type);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM user_expenses WHERE user_id = :user_id AND user_expense = :expense_type",
            nativeQuery = true
    )
    void deleteUserExpense(@Param("user_id") Integer userId,
                      @Param("expense_type") String expenseType);

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
    List<AllExpenseeResponsedto>  allUserExpense (@Param("userId") Integer userId);

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
    WHERE (ue.user_id =:userId and month(ue.expense_date)=:monthNumber)""")
    List<AllExpenseeResponsedto>  allUserExpenseByMonth (@Param("userId") Integer userId,@Param("monthNumber") Integer monthNumber);











}
