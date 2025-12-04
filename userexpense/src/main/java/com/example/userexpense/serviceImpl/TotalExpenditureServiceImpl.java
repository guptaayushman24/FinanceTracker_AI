package com.example.userexpense.serviceImpl;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.*;
import com.example.userexpense.repository.ExpenseSchedulerRepository;
import com.example.userexpense.repository.PaymentModeRepository;
import com.example.userexpense.service.TotalExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TotalExpenditureServiceImpl implements TotalExpenditureService {
    @Autowired
    PaymentModeRepository paymentModeRepository;
    @Override
    public TotalExpenseMonthResponsedto totalExpenseMonthResponsedto(Integer user_id, TotalExpenseMonthRequestdto totalExpenseMonthRequestdto){
        TotalExpenseMonthResponsedto totalExpenseMonthResponsedto = paymentModeRepository.totalExpenseMonthResponsedto(user_id,totalExpenseMonthRequestdto.getMonth());
        totalExpenseMonthResponsedto.setMessage("Expense of the"+" "+totalExpenseMonthRequestdto.getMonth());
        totalExpenseMonthResponsedto.setSum(totalExpenseMonthResponsedto.getSum());
        return totalExpenseMonthResponsedto;
    }

    @Override
    public TotalExpenseYearResponsedto totalExpenseYearResponsedto(Integer user_id, TotalExpenseYearRequestdto totalExpenseYearRequestdto) {
        TotalExpenseYearResponsedto totalExpenseYearResponsedto = paymentModeRepository.totalExpenseYearesponsedto(user_id,totalExpenseYearRequestdto.getYear());
        totalExpenseYearResponsedto.setMessage("Expense of the year"+" "+totalExpenseYearRequestdto.getYear());
        totalExpenseYearResponsedto.setSum(totalExpenseYearResponsedto.getSum());
        return totalExpenseYearResponsedto;
    }

    @Override
    public TotalExpenseCurrentDayResponsedto totalExpenseCurrentDayResponsedto(Integer user_id, LocalDate localDate) {
       TotalExpenseCurrentDayResponsedto totalExpenseCurrentDayResponsedto = paymentModeRepository.totalExpenseCurrentResponsedto(user_id,localDate);
        totalExpenseCurrentDayResponsedto.setMessage("Expense of today is"+" "+totalExpenseCurrentDayResponsedto.getSum());
        totalExpenseCurrentDayResponsedto.setSum(totalExpenseCurrentDayResponsedto.getSum());

        return totalExpenseCurrentDayResponsedto;
    }
}
