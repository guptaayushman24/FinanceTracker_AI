package com.example.userexpense.serviceImpl;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.*;
import com.example.userexpense.exception.HandleEmptyStringException;
import com.example.userexpense.exception.HandleExpenseNotExist;
import com.example.userexpense.exception.HandleRecordException;
import com.example.userexpense.exception.HandleSortExpenseException;
import com.example.userexpense.model.PaymentMode;
import com.example.userexpense.model.UserExpense;
import com.example.userexpense.repository.PaymentModeRepository;
import com.example.userexpense.repository.UserExpenseRepository;
import com.example.userexpense.service.UserExpenseService;
import com.example.userexpense.dto.AllExpenseeResponsedto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
            throw new HandleRecordException("Please add the expense");
        }
        userExpense.setValue(userExpenseRequestdto.getValue());
        userExpense.setDescription(userExpenseRequestdto.getDescription());
        userExpense.setExpenseDate(userExpenseRequestdto.getExpense_date());


        // save the modeOfPaymnet in the another table PaymentMode
        paymentMode.setUser_id(userLoginId.getUserId());
        paymentMode.setPaymentMode(userExpenseRequestdto.getPaymentMode());
        paymentMode.setExpenseDate(userExpenseRequestdto.getExpense_date());
         userExpense.setPaymentMode(paymentMode);

        log.info(userExpenseRequestdto.getPaymentMode());




        PaymentMode savePaymentMode = paymentModeRepository.save(paymentMode);

        // Now attach it to UserExpense
        userExpense.setPaymentMode(savePaymentMode);

          // Save UserExpense with correct FK
        UserExpense saved = userExpenseRepository.save(userExpense);



        UserExpenseResponsedto userExpenseResponsedto = new UserExpenseResponsedto();
        userExpenseResponsedto.setExpenseType(saved.getExpenseType());
        userExpenseResponsedto.setValue(saved.getValue());
        userExpenseResponsedto.setDescription(saved.getDescription());
        userExpenseResponsedto.setPaymentMode(savePaymentMode.getPaymentMode());
        userExpenseResponsedto.setExpense_date(saved.getExpenseDate());
        userExpenseResponsedto.setMessage("Expense Record Saved Successfully!!!!");
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
        // Check if user has registered the expense
        List<ExpenseExistdto> list = userExpenseRepository.expenseExist(userLoginId.getUserId());
        if (!list.contains(deleteUserExpenseRequestdto.getExpenseTobeDeleted())){
            throw new HandleExpenseNotExist("You have not registered for the"+" "+deleteUserExpenseRequestdto.getExpenseTobeDeleted()+" Expense but you can register the expense");
        }

        userExpenseRepository.deleteUserExpense(userLoginId.getUserId(), deleteUserExpenseRequestdto.getExpenseTobeDeleted());
        deleteUserExpenseResponsedto.setExpenseTobeDeleted(deleteUserExpenseRequestdto.getExpenseTobeDeleted() + " " + "Expense Deleted Successfully");
        return deleteUserExpenseResponsedto;


    }

    @Override
    public List<SortExpenseResposedto> sortExpense(SortExpenseRequestdto sortExpenseRequestdto) {
           SortExpenseResposedto sortExpenseResposedto = new SortExpenseResposedto();
           List<SortExpenseResposedto> listOfExpenses = userExpenseRepository.allUserExpensebyId(userLoginId.getUserId());
           List<SortExpenseResposedto> allExpensessorted = new ArrayList<>();



           // Sort by ascending or descinding order on the basis of request
           if (sortExpenseRequestdto.getSortOrder().equals("asc")) {
               PriorityQueue<SortExpenseResposedto> userExpense = new PriorityQueue<>((a, b) -> Integer.compare(a.getValue(), b.getValue()));

               userExpense.addAll(listOfExpenses);

               while (userExpense.size()>0){
                   allExpensessorted.add(userExpense.poll());
               }
           } else if (sortExpenseRequestdto.getSortOrder().equals("desc")) {

                PriorityQueue<SortExpenseResposedto> userExpense = new PriorityQueue<>((a, b) -> Integer.compare(b.getValue(), a.getValue()));

               userExpense.addAll(listOfExpenses);
               while (userExpense.size()>0){
                   allExpensessorted.add(userExpense.poll());
               }

           }
           else{
               throw new HandleSortExpenseException("Please select the asc or desc for using the sorting feature");
           }
           return allExpensessorted;
    }

    @Override
    public List<AllExpenseeResponsedto> allExpense(Integer userId) {
        return userExpenseRepository.allUserExpense(userLoginId.getUserId());
    }

    @Override
    public List<AllExpenseeResponsedto> allExpensebyMonth(Integer monthNumber,String [] monthList) {
        return userExpenseRepository.allUserExpenseByMonth(userLoginId.getUserId(),monthNumber);
    }

    @Override
    public IndivisualExpensesqldto indivisualUserExpense(String expenseType) {
        // Check if user has registered the expense
        List<ExpenseExistdto> list = userExpenseRepository.expenseExist(userLoginId.getUserId());
        if (expenseType.length()==0){
            throw new HandleEmptyStringException("Please enter the Expense Type");
        }
        
        if (!list.contains(expenseType)){
            throw new HandleExpenseNotExist("You have not registered for the"+" "+expenseType+" Expense but you can register the expense");
        }
        return userExpenseRepository.indivisualExpense(userLoginId.getUserId(),expenseType);
    }
}
