package com.example.userexpense.service;

import com.example.userexpense.dto.AllExpenseeResponsedto;
import com.example.userexpense.dto.ExpenseSchedulerRequestdto;
import com.example.userexpense.dto.ExpenseSchedulerResponsedto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ExpenseSchedulerService {
    ExpenseSchedulerResponsedto expenseScheduler (ExpenseSchedulerRequestdto expenseSchedulerRequestdto);
   // List<AllExpenseeResponsedto> expenseRecordScheduler (Integer userId, LocalDate expenseDate);
}
