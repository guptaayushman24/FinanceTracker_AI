package com.example.userexpense.service;

import com.example.userexpense.dto.ExpenseSchedulerRequestdto;
import com.example.userexpense.dto.ExpenseSchedulerResponsedto;
import org.springframework.stereotype.Service;

@Service
public interface ExpenseSchedulerService {
    ExpenseSchedulerResponsedto expenseScheduler (ExpenseSchedulerRequestdto expenseSchedulerRequestdto);
}
