package com.example.userexpense.service;

import com.example.userexpense.dto.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface TotalExpenditureService {
    TotalExpenseMonthResponsedto totalExpenseMonthResponsedto (Integer user_id, TotalExpenseMonthRequestdto totalExpenseMonthRequestdto);
    TotalExpenseYearResponsedto totalExpenseYearResponsedto (Integer user_id, TotalExpenseYearRequestdto totalExpenseYearRequestdto);
    TotalExpenseCurrentDayResponsedto totalExpenseCurrentDayResponsedto(Integer user_id, LocalDate localDate);
}
