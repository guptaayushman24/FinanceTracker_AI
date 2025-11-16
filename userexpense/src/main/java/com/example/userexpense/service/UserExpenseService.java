package com.example.userexpense.service;

import com.example.userexpense.dto.AddUserExpenseRequestdto;
import com.example.userexpense.dto.AddUserExpenseResponsedto;
import com.example.userexpense.dto.UserExpenseRequestdto;
import com.example.userexpense.dto.UserExpenseResponsedto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserExpenseService {
    UserExpenseResponsedto userExpense(UserExpenseRequestdto userExpenseRequestdto);
    List<AddUserExpenseResponsedto> addUserExpense (AddUserExpenseRequestdto addUserExpenseRequestdto);
}
