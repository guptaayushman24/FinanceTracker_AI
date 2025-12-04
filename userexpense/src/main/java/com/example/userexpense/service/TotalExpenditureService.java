package com.example.userexpense.service;

import com.example.userexpense.dto.TotalExpenseMonthRequestdto;
import com.example.userexpense.dto.TotalExpenseMonthResponsedto;
import com.example.userexpense.dto.TotalExpenseYearRequestdto;
import com.example.userexpense.dto.TotalExpenseYearResponsedto;
import org.springframework.stereotype.Service;

@Service
public interface TotalExpenditureService {
    TotalExpenseMonthResponsedto totalExpenseMonthResponsedto (Integer user_id, TotalExpenseMonthRequestdto totalExpenseMonthRequestdto);
    TotalExpenseYearResponsedto totalExpenseYearResponsedto (Integer user_id, TotalExpenseYearRequestdto totalExpenseYearRequestdto);
}
