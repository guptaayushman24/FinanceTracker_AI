package com.example.userexpense.repository;

import com.example.userexpense.dto.AddUserExpenseResponsedto;
import com.example.userexpense.dto.UserExpenseRequestdto;
import com.example.userexpense.dto.UserExpenseResponsedto;
import com.example.userexpense.model.UserExpense;
import org.springframework.data.jpa.repository.JpaRepository;
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

    @Query(
            value = """
                      SELECT column_name
                         FROM information_schema.columns
                         WHERE table_schema = 'financetrackerai'
                         AND table_name = 'Expense'
                         AND column_name IN (user_expense);
                    """
    )ASER TFGYHU9IJ0K-OL|
    408259AAddUserExpenseResponsedto addUserExpenseResponsedto (@Param("user_expense") String user_expense);



}
