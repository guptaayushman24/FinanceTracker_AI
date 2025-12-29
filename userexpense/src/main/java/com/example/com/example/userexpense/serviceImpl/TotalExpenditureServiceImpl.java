package com.example.userexpense.serviceImpl;


import com.example.userexpense.dto.*;
import com.example.userexpense.exception.*;
import com.example.userexpense.repository.ExpenseSchedulerRepository;
import com.example.userexpense.repository.PaymentModeRepository;
import com.example.userexpense.service.TotalExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

@Service
public class TotalExpenditureServiceImpl implements TotalExpenditureService {
    @Autowired
    PaymentModeRepository paymentModeRepository;
    @Override
    public TotalExpenseMonthResponsedto totalExpenseMonthResponsedto(Integer user_id, TotalExpenseMonthRequestdto totalExpenseMonthRequestdto){
        String[] monthList = {
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        };
        List<String> list = Arrays.asList(monthList);
        if (totalExpenseMonthRequestdto.getMonth().isEmpty() && (totalExpenseMonthRequestdto.getYear()<2000 || totalExpenseMonthRequestdto.getYear()> Year.now().getValue())){
            throw new HandleEmptyStringException("Please select the month");
        }
        if (!list.contains(totalExpenseMonthRequestdto.getMonth())){
            throw new HandleExpenseExceptionByMonth("Please select the valid month");
        }
        TotalExpenseMonthResponsedto totalExpenseMonthResponsedto = paymentModeRepository.totalExpenseMonthResponsedto(user_id,totalExpenseMonthRequestdto.getMonth(),totalExpenseMonthRequestdto.getYear());
        if (totalExpenseMonthResponsedto.getSum()==null){
            totalExpenseMonthResponsedto.setSum(0L);
        }
        totalExpenseMonthResponsedto.setMessage("Expense of the"+" "+totalExpenseMonthRequestdto.getMonth());
        totalExpenseMonthResponsedto.setSum(totalExpenseMonthResponsedto.getSum());
        return totalExpenseMonthResponsedto;
    }

    @Override
    public TotalExpenseYearResponsedto totalExpenseYearResponsedto(Integer user_id, TotalExpenseYearRequestdto totalExpenseYearRequestdto) {
        TotalExpenseYearResponsedto totalExpenseYearResponsedto = paymentModeRepository.totalExpenseYearesponsedto(user_id,totalExpenseYearRequestdto.getYear());
        totalExpenseYearResponsedto.setMessage("Expense of the year"+" "+totalExpenseYearRequestdto.getYear());
        if (totalExpenseYearResponsedto.getSum()==null){
            totalExpenseYearResponsedto.setSum(0L);
        }

        int year = Integer.parseInt(totalExpenseYearRequestdto.getYear());
        if (year<2000 || year>Year.now().getValue()){
            throw new HandleInvalidYearException("Year must be between 2000 and current year");
        }

        totalExpenseYearResponsedto.setSum(totalExpenseYearResponsedto.getSum());
        return totalExpenseYearResponsedto;
    }

    @Override
    public TotalExpenseCurrentDayResponsedto totalExpenseCurrentDayResponsedto(Integer user_id, LocalDate localDate) {
       TotalExpenseCurrentDayResponsedto totalExpenseCurrentDayResponsedto = paymentModeRepository.totalExpenseCurrentResponsedto(user_id,localDate);
       if (totalExpenseCurrentDayResponsedto.getSum()==null){
           totalExpenseCurrentDayResponsedto.setSum(0L);
       }
        totalExpenseCurrentDayResponsedto.setMessage("Expense of today is"+" "+totalExpenseCurrentDayResponsedto.getSum());
        totalExpenseCurrentDayResponsedto.setSum(totalExpenseCurrentDayResponsedto.getSum());

        return totalExpenseCurrentDayResponsedto;
    }

    @Override
    public TotalExpenseYearPaymentModeResponsedto totalExpenseYearPaymentModeResponsedto(Integer user_id, TotalExpenseYearPaymentModeRequestdto totalExpenseYearPaymentModeRequestdto) {
        if (totalExpenseYearPaymentModeRequestdto.getPaymentMode().isEmpty()){
            throw new HandlePaymentModeException("Please select the Payment Mode");
        }
        if (!totalExpenseYearPaymentModeRequestdto.getPaymentMode().equals("UPI") && !totalExpenseYearPaymentModeRequestdto.getPaymentMode().equals("CASH")){
            throw new HandlePaymentModeException("Choose UPI or CASH");
        }
        int year = Integer.parseInt(totalExpenseYearPaymentModeRequestdto.getYear());
        if (year<2000 || year>Year.now().getValue()){
            throw new HandleInvalidYearException("Year must be between 2000 and current year");
        }

        TotalExpenseYearPaymentModeResponsedto totalExpenseYearPaymentModeResponsedto = paymentModeRepository.totalExpenseYearesponsePaymentModedto(user_id,totalExpenseYearPaymentModeRequestdto.getYear(),totalExpenseYearPaymentModeRequestdto.getPaymentMode());
        totalExpenseYearPaymentModeResponsedto.setMessage("Expense by"+" "+totalExpenseYearPaymentModeRequestdto.getPaymentMode());
        totalExpenseYearPaymentModeResponsedto.setSum(totalExpenseYearPaymentModeResponsedto.getSum());

        return totalExpenseYearPaymentModeResponsedto;
    }

    @Override
    public TotalExpenseMonthPaymentModeResponsedto totalExpenseMonthPaymentModeResponsedto(Integer user_id, TotalExpenseMonthPaymentModeRequestdto totalExpenseMonthPaymentModeRequestdto) {
        if (totalExpenseMonthPaymentModeRequestdto.getPaymentMode().isEmpty()){
            throw new HandlePaymentModeException("Please select the Payment Mode");
        }
        if (!totalExpenseMonthPaymentModeRequestdto.getPaymentMode().equals("UPI") && !totalExpenseMonthPaymentModeRequestdto.getPaymentMode().equals("CASH")){
            throw new HandlePaymentModeException("Choose UPI or CASH");
        }
        String[] monthList = {
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        };
        List<String> list = Arrays.asList(monthList);
        if (!list.contains(totalExpenseMonthPaymentModeRequestdto.getMonth())){
            throw new HandleExpenseExceptionByMonth("Select the valid month");
        }
        TotalExpenseMonthPaymentModeResponsedto totalExpenseMonthPaymentModeResponsedto = paymentModeRepository.totalExpenseMonthPaymentModeResponsedto(user_id,totalExpenseMonthPaymentModeRequestdto.getMonth(),totalExpenseMonthPaymentModeRequestdto.getPaymentMode(),totalExpenseMonthPaymentModeRequestdto.getYear());
        if (totalExpenseMonthPaymentModeResponsedto.getSum()==null){
            totalExpenseMonthPaymentModeResponsedto.setSum(0L);
        }
        totalExpenseMonthPaymentModeResponsedto.setMessage("Expense by"+" "+ totalExpenseMonthPaymentModeRequestdto.getPaymentMode()+" "+ "in"+" "+totalExpenseMonthPaymentModeRequestdto.getMonth()+" "+"month");
        totalExpenseMonthPaymentModeResponsedto.setSum(totalExpenseMonthPaymentModeResponsedto.getSum());

        return totalExpenseMonthPaymentModeResponsedto;
    }

    @Override
    public TotalExpenseCurrentDayPaymentModeResponsedto totalExpenseCurrentDayPaymentModeResponsedto(Integer user_id, LocalDate localDate, String paymentMode) {
        TotalExpenseCurrentDayPaymentModeResponsedto totalExpenseCurrentDayPaymentModeResponsedto = paymentModeRepository.totalExpenseCurrentDayPaymentResponsedto(user_id,localDate,paymentMode);
        if (totalExpenseCurrentDayPaymentModeResponsedto.getSum()==null){
            totalExpenseCurrentDayPaymentModeResponsedto.setSum(0L);
        }
        if (!paymentMode.equals("UPI") && !paymentMode.equals("CASH")){
            throw new HandlePaymentModeException("Choose UPI or CASH");
        }
        totalExpenseCurrentDayPaymentModeResponsedto.setMessage("Expense by"+" "+paymentMode+" "+"today is");
        totalExpenseCurrentDayPaymentModeResponsedto.setSum(totalExpenseCurrentDayPaymentModeResponsedto.getSum());

        return totalExpenseCurrentDayPaymentModeResponsedto;
    }


}
