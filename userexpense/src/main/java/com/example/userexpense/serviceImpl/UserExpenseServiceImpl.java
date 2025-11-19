package com.example.userexpense.serviceImpl;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.*;
import com.example.userexpense.model.PaymentMode;
import com.example.userexpense.model.UserExpense;
import com.example.userexpense.repository.PaymentModeRepository;
import com.example.userexpense.repository.UserExpenseRepository;
import com.example.userexpense.service.UserExpenseService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

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
    public UserExpenseResponsedto userExpense(UserExpenseRequestdto userExpenseRequestdto) {
        UserExpense userExpense = new UserExpense();
        PaymentMode paymentMode = new PaymentMode();

        // From The Kafka Consumer
        System.out.println("User id is" + " " + userLoginId.getUserId());
        userExpense.setUser_id(userLoginId.getUserId());
        // check if the user has registered against the expense or not
        // check if the expense is present in the list or not if present add and if not return the response
        HashSet<String> userExpenseExist = userExpenseRepository.checkUserExpenseExist(userLoginId.getUserId());
        if (userExpenseExist.contains(userExpenseRequestdto.getExpenseType())) {
            userExpense.setExpenseType(userExpenseRequestdto.getExpenseType());
        } else {
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
        userExpenseRepository.addNewUserExpense(userLoginId.getUserId(), addUserExpenseRequestdto.getNewUserExpense());
        addUserExpenseResponsedto.setNewUserExpense(addUserExpenseRequestdto.getNewUserExpense() + " " + "Expense Added Successfully");
        return addUserExpenseResponsedto;
    }

    @Override
    public DeleteUserExpenseResponsedto deleteUserExpense(DeleteUserExpenseRequestdto deleteUserExpenseRequestdto) {
        DeleteUserExpenseResponsedto deleteUserExpenseResponsedto = new DeleteUserExpenseResponsedto();
        userExpenseRepository.deleteUserExpense(userLoginId.getUserId(), deleteUserExpenseRequestdto.getExpenseTobeDeleted());
        deleteUserExpenseResponsedto.setExpenseTobeDeleted(deleteUserExpenseRequestdto.getExpenseTobeDeleted() + " " + "Expense Deleted Successfully");
        return deleteUserExpenseResponsedto;
    }

    @Override
    public SortExpenseResposedto sortExpense(SortExpenseRequestdto sortExpenseRequestdto) {
        SortExpenseResposedto sortExpenseResposedto = new SortExpenseResposedto();
        List<SortExpenseResposedto> listOfExpenses = userExpenseRepository.allUserExpensebyId(userLoginId.getUserId());


        // Sort by ascending or descinding order on the basis of request
        if (sortExpenseRequestdto.getSortOrder().equals("asc")) {
            PriorityQueue<SortExpenseResposedto> userExpense = new PriorityQueue<>((a, b) -> Integer.compare(a.getValue(), b.getValue()));
            for (int i = 0; i < listOfExpenses.size(); i++) {
                userExpense.add(listOfExpenses.get(i));
            }

            while (userExpense.size() > 0) {
                SortExpenseResposedto sortExpenseResposedto1 = userExpense.peek();
                sortExpenseResposedto.setExpenseType(sortExpenseResposedto1.getExpenseType());
                sortExpenseResposedto.setValue(sortExpenseResposedto1.getValue());
                sortExpenseResposedto.setDescription(sortExpenseResposedto1.getDescription());
                sortExpenseResposedto.setPaymentMode(sortExpenseResposedto1.getPaymentMode());
                sortExpenseResposedto.setPaymentMode(sortExpenseResposedto1.getPaymentMode());

                return sortExpenseResposedto1;
            }
        } else if (sortExpenseRequestdto.getSortOrder().equals("desc")) {
            if (sortExpenseRequestdto.getSortOrder().equals("asc")) {
                PriorityQueue<SortExpenseResposedto> userExpense = new PriorityQueue<>((a, b) -> Integer.compare(b.getValue(), a.getValue()));
                for (int i = 0; i < listOfExpenses.size(); i++) {
                    userExpense.add(listOfExpenses.get(i));
                }

                while (userExpense.size() > 0) {
                    SortExpenseResposedto sortExpenseResposedto1 = userExpense.peek();
                    sortExpenseResposedto.setExpenseType(sortExpenseResposedto1.getExpenseType());
                    sortExpenseResposedto.setValue(sortExpenseResposedto1.getValue());
                    sortExpenseResposedto.setDescription(sortExpenseResposedto1.getDescription());
                    sortExpenseResposedto.setPaymentMode(sortExpenseResposedto1.getPaymentMode());
                    sortExpenseResposedto.setPaymentMode(sortExpenseResposedto1.getPaymentMode());

                    return sortExpenseResposedto1;
                }
            }

        }
        return null;
    }
}
