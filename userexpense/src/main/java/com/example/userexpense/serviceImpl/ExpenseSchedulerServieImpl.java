package com.example.userexpense.serviceImpl;


import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.ExpenseSchedulerRequestdto;
import com.example.userexpense.dto.ExpenseSchedulerResponsedto;
import com.example.userexpense.dto.ExpenseSchedulerSavedto;
import com.example.userexpense.dto.UserExpenseRequestdto;
import com.example.userexpense.model.ExpenseScheduler;
import com.example.userexpense.repository.ExpenseSchedulerRepository;
import com.example.userexpense.service.ExpenseSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
