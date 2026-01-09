package com.example.com.example.userexpense.service;

import com.example.userexpense.dto.UserExpenseResponsedto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import
import java.util.List;

@Service
public class ReddisService {
    @Autowired
    private RedisTemplate<String, List<UserExpenseResponsedto>> redistUserExpenseTemplate;
    public void saveUserCurrentDayExpense (Long id,List<UserExpenseResponsedto> userExpenseResponsedto){
        // Extract the currentDay Expense
        List<UserExpenseResponsedto> currentDayExpense =
    }
}
