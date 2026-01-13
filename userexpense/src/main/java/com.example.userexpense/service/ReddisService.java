package com.example.userexpense.service;

import com.example.userexpense.controller.UserExpense;
import com.example.userexpense.dto.AllExpenseeResponsedto;
import com.example.userexpense.dto.UserExpenseResponsedto;
import com.example.userexpense.repository.UserExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private RedisTemplate<String, List<UserExpenseResponsedto>> redistUserExpenseTemplate;
    public void saveUserCurrentDayExpense (Integer userId,List<UserExpenseResponsedto> userExpenseResponsedto){
        // id -> List<AllExpenseResponnsedto>
       long midNightTime = calculatingMidNightDate();
        redistUserExpenseTemplate.opsForValue().set(String.valueOf(userId),userExpenseResponsedto,midNightTime, TimeUnit.SECONDS);
    }

    public List<UserExpenseResponsedto> retrieveData (Integer userId){
        return redistUserExpenseTemplate.opsForValue().get(userId);
    }

    public void deleteAllReddisData(){
        redistUserExpenseTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
    }


}

