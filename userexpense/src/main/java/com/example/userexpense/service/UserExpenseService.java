package com.example.userexpense.service;

import com.example.userexpense.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserExpenseService {
    UserExpenseResponsedto userExpense(UserExpenseRequestdto userExpenseRequestdto);
    AddUserExpenseResponsedto addUserExpense (AddUserExpenseRequestdto addUserExpenseRequestdto);
    DeleteUserExpenseResponsedto deleteUserExpense(DeleteUserExpenseRequestdto deleteUserExpenseRequestdto);
    SortExpenseResposedto sortExpense (SortExpenseRequestdto sortExpenseRequestdto);
    List<AllExpenseeResponsedto> allExpense(Integer userId);
}
