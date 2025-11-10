package com.example.userexpense.service;

import com.example.userexpense.dto.UserExpenseRequestdto;
import com.example.userexpense.dto.UserExpenseResponsedto;
import org.springframework.stereotype.Service;

@Service
public interface UserExpenseService {
    UserExpenseResponsedto userExpense(UserExpenseRequestdto userExpenseRequestdto);
}
