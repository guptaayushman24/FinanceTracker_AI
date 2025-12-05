package com.example.userexpense.serviceImpl;


import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.*;
import com.example.userexpense.model.ExpenseScheduler;
import com.example.userexpense.repository.ExpenseSchedulerRepository;
import com.example.userexpense.service.ExpenseSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExpenseSchedulerServieImpl implements ExpenseSchedulerService {
    @Autowired
    ExpenseSchedulerRepository expenseSchedulerRepository;
    @Autowired
    UserLoginId userLoginId;
    @Override
    public ExpenseSchedulerResponsedto expenseScheduler(ExpenseSchedulerRequestdto expenseSchedulerRequestdto) {
        ExpenseScheduler expenseScheduler = new ExpenseScheduler();
        expenseScheduler.setUser_id(userLoginId.getUserId());
        expenseScheduler.setSchedulerEvent(expenseSchedulerRequestdto.getDays());
        expenseSchedulerRepository.save(expenseScheduler);

        ExpenseSchedulerResponsedto expenseSchedulerResponsedto = new ExpenseSchedulerResponsedto();
        expenseSchedulerResponsedto.setDays(expenseSchedulerRequestdto.getDays());
        expenseSchedulerResponsedto.setMessage("Scheduling Configuration is done !!!!");

        return expenseSchedulerResponsedto;
    }

//    @Override
//    public List<AllExpenseeResponsedto> expenseRecordScheduler(Integer userId, LocalDate expenseDate) {
//        LocalDate today = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String formattedDate = today.format(formatter);
        // return expenseSchedulerRepository.expenseRecordScheduler(userLoginId.getUserId(),expenseDate);

    // }
}
