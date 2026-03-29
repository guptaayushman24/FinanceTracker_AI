package com.example.userexpense.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;

@Service
public interface  UserExpenseBarGrpahChartByMonthService{
    public Map<String,Long> userExpenseBarGraphtByMonth (Integer userId, String monthName, Model model, Integer year);
    public Map<String,Long> userExpenseBarGraphByYear (Integer userId,Integer year,Model model);
}
