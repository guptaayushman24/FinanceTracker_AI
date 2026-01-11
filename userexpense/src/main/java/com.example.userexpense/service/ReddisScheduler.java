package com.example.userexpense.service;

import com.example.userexpense.dto.AllExpenseeResponsedto;
import com.example.userexpense.repository.UserExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;

public class ReddisScheduler {
    @Scheduled(cron = "0 5 0 * * ?") // 12:05 AM
    public void clearDailyExpenseCache() {
        redisTemplate.delete("DAILY_EXPENSE:*");
    }
    }


