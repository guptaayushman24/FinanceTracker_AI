package com.example.userexpense.service;

import com.example.userexpense.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserExpenseService {
    UserExpenseResponsedto userExpense(UserExpenseRequestdto userExpenseRequestdto);
    AddUserExpenseResponsedto addUserExpense (AddUserExpenseRequestdto addUserExpenseRequestdto);
    DeleteUserExpenseResponsedto deleteUserExpense(DeleteUserExpenseRequestdto deleteUserExpenseRequestdto);



    List<SortExpenseResposedto> sortExpense (SortExpenseRequestdto sortExpenseRequestdto);
    List<AllExpenseeResponsedto> allExpense(Integer userId);

    List<AllExpenseeResponsedto> allExpensebyMonth(Integer monthNumber);

    IndivisualExpensesqldto indivisualUserExpense (String expenseType);

}
