package com.example.userexpense.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public interface  UserExpenseBarGrpahChartByMonthService{
    public String userExpenseBarGraphtByMonth (Integer userId, String monthName, Model model);
}
