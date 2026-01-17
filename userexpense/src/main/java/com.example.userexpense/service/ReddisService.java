package com.example.userexpense.service;

import com.example.userexpense.controller.UserExpense;
import com.example.userexpense.dto.AllExpenseeResponsedto;
import com.example.userexpense.dto.UserExpenseResponsedto;
import com.example.userexpense.repository.UserExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.core.Local;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ReddisService {
    public static long calculatingMidNightDate(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midNight = now.toLocalDate().plusDays(1).atStartOfDay();
        long secondsUntilMidNight = Duration.between(now,midNight).getSeconds();

        return secondsUntilMidNight;
    }
    @Autowired
    private RedisTemplate<Integer, List<UserExpenseResponsedto>> redistUserExpenseTemplate;
    public void saveUserCurrentDayExpense (Integer userId,List<UserExpenseResponsedto> userExpenseResponsedto){
        // id -> List<AllExpenseResponnsedto>
       long midNightTime = calculatingMidNightDate();
        redistUserExpenseTemplate.opsForList().rightPushAll(userId,userExpenseResponsedto);
    }

    public List<List<UserExpenseResponsedto>> retrieveData (Integer userId){
        // return redistUserExpenseTemplate.opsForValue().get(userId);
        List<UserExpenseResponsedto> userExpenseResponsedto = new ArrayList<>();
        List<List<UserExpenseResponsedto>> list =
                redistUserExpenseTemplate.opsForList().range(userId,0,-1);

        return list;
    }

    public void deleteAllReddisData(){
        redistUserExpenseTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
    }


}

