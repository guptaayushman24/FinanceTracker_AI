package com.example.userexpense.service;

import com.example.userexpense.dto.UserExpensePieChartByMonthdto;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@Service
public interface UserExpensePieChartByMonthService {
    public Map<String,Long> userExpensePieChartByMonth (Integer userId, String monthName, Model model, Integer year);
    public Map<String,Long> userExpensePieChartByYear (Integer userId,Integer year,Model model);
}
