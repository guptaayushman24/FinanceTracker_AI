package com.example.userexpense.service;

import com.example.userexpense.dto.TotalExpenseMonthRequestdto;
import com.example.userexpense.dto.TotalExpenseMonthResponsedto;
import org.springframework.stereotype.Service;

@Service
public interface TotalExpenditureService {
    TotalExpenseMonthResponsedto totalExpenseMonthResponsedto (Integer user_id, TotalExpenseMonthRequestdto totalExpenseMonthRequestdto);
}
