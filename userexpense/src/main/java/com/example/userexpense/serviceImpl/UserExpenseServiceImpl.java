package com.example.userexpense.serviceImpl;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.UserExpenseRequestdto;
import com.example.userexpense.dto.UserExpenseResponsedto;
import com.example.userexpense.model.UserExpense;
import com.example.userexpense.repository.UserExpenseRepository;
import com.example.userexpense.service.UserExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserExpenseServiceImpl implements UserExpenseService {
    @Autowired
    UserExpenseRepository userExpenseRepository;
    @Autowired
    UserLoginId userLoginId;
    @Override
    public UserExpenseResponsedto userExpense(UserExpenseRequestdto userExpenseRequestdto){
        UserExpense userExpense = new UserExpense();
        // From The Kafka Consumer
        System.out.println("User id is"+" "+userLoginId.getUserId());
        userExpense.setUser_id(userLoginId.getUserId());
        userExpense.setExpenseType(userExpenseRequestdto.getExpenseType());
        userExpense.setValue(userExpenseRequestdto.getValue());
        userExpense.setDescription(userExpenseRequestdto.getDescription());
        // save the modeOfPaymnet in the another table PaymentMode

        UserExpense saved = userExpenseRepository.save(userExpense);

        UserExpenseResponsedto userExpenseResponsedto = new UserExpenseResponsedto();
        userExpenseResponsedto.setExpenseType(saved.getExpenseType());
        userExpenseResponsedto.setValue(saved.getValue());
        userExpenseResponsedto.setDescription(saved.getDescription());

        return userExpenseResponsedto;
    }
}
