package com.example.userexpense.service;

import com.example.userexpense.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserExpenseService {
    UserExpenseResponsedto userExpense(UserExpenseRequestdto userExpenseRequestdto,Integer userId);
    AddUserExpenseResponsedto addUserExpense (AddUserExpenseRequestdto addUserExpenseRequestdto,Integer userId);
    DeleteUserExpenseResponsedto deleteUserExpense(DeleteUserExpenseRequestdto deleteUserExpenseRequestdto,Integer userId);



    List<SortExpenseResposedto> sortExpense (SortExpenseRequestdto sortExpenseRequestdto,Integer userId);
    List<AllExpenseeResponsedto> allExpense(Integer userId);

    List<AllExpenseeResponsedto> allExpensebyMonth(Integer monthNumber,String [] monthList,Integer userId);

    IndivisualExpensesqldto indivisualUserExpense (String expenseType,Integer userId);

}
