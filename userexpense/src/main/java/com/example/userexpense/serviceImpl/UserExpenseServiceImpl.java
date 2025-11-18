package com.example.userexpense.serviceImpl;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.AddUserExpenseRequestdto;
import com.example.userexpense.dto.AddUserExpenseResponsedto;
import com.example.userexpense.dto.UserExpenseRequestdto;
import com.example.userexpense.dto.UserExpenseResponsedto;
import com.example.userexpense.model.PaymentMode;
import com.example.userexpense.model.UserExpense;
import com.example.userexpense.repository.PaymentModeRepository;
import com.example.userexpense.repository.UserExpenseRepository;
import com.example.userexpense.service.UserExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
public class UserExpenseServiceImpl implements UserExpenseService {
    @Autowired
    UserExpenseRepository userExpenseRepository;
    @Autowired
    UserLoginId userLoginId;
    @Autowired
    PaymentModeRepository paymentModeRepository;
    @Override
    public UserExpenseResponsedto userExpense(UserExpenseRequestdto userExpenseRequestdto){
        UserExpense userExpense = new UserExpense();
        PaymentMode paymentMode = new PaymentMode();

        // From The Kafka Consumer
        System.out.println("User id is"+" "+userLoginId.getUserId());
        userExpense.setUser_id(userLoginId.getUserId());
        // check if the user has registered against the expense or not
        // check if the expense is present in the list or not if present add and if not return the response
        HashSet<String> userExpenseExist = userExpenseRepository.checkUserExpenseExist(userLoginId.getUserId());
        if (userExpenseExist.contains(userExpenseRequestdto.getExpenseType())){
            userExpense.setExpenseType(userExpenseRequestdto.getExpenseType());
        }
        else{
            UserExpenseResponsedto userExpenseResponsedto = new UserExpenseResponsedto();
            userExpenseResponsedto.setExpenseType(null);
            userExpenseResponsedto.setValue(null);
            userExpenseResponsedto.setDescription(null);
            userExpenseResponsedto.setPaymentMode(null);
            userExpenseResponsedto.setExpense_date(null);
            return userExpenseResponsedto;
        }
        userExpense.setValue(userExpenseRequestdto.getValue());
        userExpense.setDescription(userExpenseRequestdto.getDescription());
        userExpense.setExpenseDate(userExpenseRequestdto.getExpense_date());
        // save the modeOfPaymnet in the another table PaymentMode
        paymentMode.setUser_id(userLoginId.getUserId());
        paymentMode.setPaymentMode(userExpenseRequestdto.getPaymentMode());
        paymentMode.setExpenseDate(userExpenseRequestdto.getExpense_date());
        log.info(userExpenseRequestdto.getPaymentMode());



        UserExpense saved = userExpenseRepository.save(userExpense);
        PaymentMode savePaymentMode = paymentModeRepository.save(paymentMode);

        UserExpenseResponsedto userExpenseResponsedto = new UserExpenseResponsedto();
        userExpenseResponsedto.setExpenseType(saved.getExpenseType());
        userExpenseResponsedto.setValue(saved.getValue());
        userExpenseResponsedto.setDescription(saved.getDescription());
        userExpenseResponsedto.setPaymentMode(savePaymentMode.getPaymentMode());
        userExpenseResponsedto.setExpense_date(saved.getExpenseDate());
        log.info(userExpenseResponsedto.getPaymentMode());


        return userExpenseResponsedto;
    }

    @Override
    public AddUserExpenseResponsedto addUserExpense(AddUserExpenseRequestdto addUserExpenseRequestdto) {
        AddUserExpenseResponsedto addUserExpenseResponsedto = new AddUserExpenseResponsedto();
        String newUserExpense =  userExpenseRepository.addNewUserExpense(userLoginId.getUserId(),addUserExpenseRequestdto.getNewUserExpense());
        addUserExpenseResponsedto.setNewUserExpense(newUserExpense);
        return addUserExpenseResponsedto;
    }


}
