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

    @Override
    public TotalExpenseYearPaymentModeResponsedto totalExpenseYearPaymentModeResponsedto(Integer user_id, TotalExpenseYearPaymentModeRequestdto totalExpenseYearPaymentModeRequestdto) {
        TotalExpenseYearPaymentModeResponsedto totalExpenseYearPaymentModeResponsedto = paymentModeRepository.totalExpenseYearesponsePaymentModedto(user_id,totalExpenseYearPaymentModeRequestdto.getYear(),totalExpenseYearPaymentModeRequestdto.getPaymentMode());
        totalExpenseYearPaymentModeResponsedto.setMessage("Expense by"+" "+totalExpenseYearPaymentModeRequestdto.getPaymentMode());
        totalExpenseYearPaymentModeResponsedto.setSum(totalExpenseYearPaymentModeResponsedto.getSum());

        return totalExpenseYearPaymentModeResponsedto;
    }

    @Override
    public TotalExpenseMonthPaymentModeResponsedto totalExpenseMonthPaymentModeResponsedto(Integer user_id, TotalExpenseMonthPaymentModeRequestdto totalExpenseMonthPaymentModeRequestdto) {
        TotalExpenseMonthPaymentModeResponsedto totalExpenseMonthPaymentModeResponsedto = paymentModeRepository.totalExpenseMonthPaymentModeResponsedto(user_id,totalExpenseMonthPaymentModeRequestdto.getMonth(),totalExpenseMonthPaymentModeRequestdto.getPaymentMode());
        totalExpenseMonthPaymentModeResponsedto.setMessage("Expense by"+" "+ totalExpenseMonthPaymentModeRequestdto.getPaymentMode()+" "+ "in"+" "+totalExpenseMonthPaymentModeRequestdto.getMonth()+" "+"month");
        totalExpenseMonthPaymentModeResponsedto.setSum(totalExpenseMonthPaymentModeResponsedto.getSum());

        return totalExpenseMonthPaymentModeResponsedto;
    }

    @Override
    public TotalExpenseCurrentDayPaymentModeResponsedto totalExpenseCurrentDayPaymentModeResponsedto(Integer user_id, LocalDate localDate, String paymentMode) {
        TotalExpenseCurrentDayPaymentModeResponsedto totalExpenseCurrentDayPaymentModeResponsedto = paymentModeRepository.totalExpenseCurrentDayPaymentResponsedto(user_id,localDate,paymentMode);
        totalExpenseCurrentDayPaymentModeResponsedto.setMessage("Expense by"+" "+paymentMode+" "+"today is");
        totalExpenseCurrentDayPaymentModeResponsedto.setSum(totalExpenseCurrentDayPaymentModeResponsedto.getSum());

        return totalExpenseCurrentDayPaymentModeResponsedto;
    }


}
