package com.example.userexpense.repository;

import com.example.userexpense.dto.PaymentModeFilterResponsedto;
import com.example.userexpense.dto.UserExpenseRequestdto;
import com.example.userexpense.dto.UserExpenseResponsedto;
import com.example.userexpense.model.UserExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExpenseRepository extends JpaRepository<UserExpense,Integer> {
    UserExpenseResponsedto save(UserExpenseRequestdto userExpenseRequestdto);
    PaymentModeFilterResponsedto findBy
}
