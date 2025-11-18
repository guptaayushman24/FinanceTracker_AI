package com.example.userexpense.repository;

import com.example.userexpense.dto.AddUserExpenseResponsedto;
import com.example.userexpense.dto.UserExpenseRequestdto;
import com.example.userexpense.dto.UserExpenseResponsedto;
import com.example.userexpense.model.UserExpense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

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



}
