package com.example.userexpense.service;

import com.example.userexpense.dto.UserExpensePieChartByMonthdto;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public interface UserExpensePieChartByMonthService {
    public String userExpensePieChartByMonth (Integer userId, String monthName, Model model);
}
