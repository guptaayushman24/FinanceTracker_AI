package com.example.userexpense.service;

import com.example.userexpense.dto.AllExpenseeResponsedto;
import com.example.userexpense.dto.UserExpenseResponsedto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReddisService {
    @Autowired
    private RedisTemplate<String, List<AllExpenseeResponsedto>> redistUserExpenseTemplate;
    public void saveUserCurrentDayExpense (Long id,List<UserExpenseResponsedto> userExpenseResponsedto){
        // Extract the currentDay Expense
        // List<UserExpenseResponsedto> currentDayExpense =
    }
}
