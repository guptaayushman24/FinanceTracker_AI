package com.example.userexpense.serviceImpl;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.TotalExpenseMonthRequestdto;
import com.example.userexpense.dto.TotalExpenseMonthResponsedto;
import com.example.userexpense.repository.ExpenseSchedulerRepository;
import com.example.userexpense.repository.PaymentModeRepository;
import com.example.userexpense.service.TotalExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
