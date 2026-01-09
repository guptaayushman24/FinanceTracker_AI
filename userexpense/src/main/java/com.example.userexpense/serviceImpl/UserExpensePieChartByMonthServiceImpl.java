package com.example.userexpense.serviceImpl;

import com.example.userexpense.dto.UserExpensePieChartByMonthdto;
import com.example.userexpense.exception.HandleEmptyStringException;
import com.example.userexpense.exception.HandleExpenseExceptionByMonth;
import com.example.userexpense.exception.HandleInvalidYearException;
import com.example.userexpense.repository.UserExpenseRepository;
import com.example.userexpense.service.UserExpensePieChartByMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class UserExpensePieChartByMonthServiceImpl implements UserExpensePieChartByMonthService {
    @Autowired
    UserExpenseRepository userExpenseRepository;
    @Override
    public String userExpensePieChartByMonth(Integer userId, String monthName,Model model,Integer year) {
        List<UserExpensePieChartByMonthdto> listOfExpense = userExpenseRepository.userExpensePieChartByMonth(userId,monthName,year);
        String[] monthList = {
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        };
        if (monthName.isEmpty()){
            throw new HandleEmptyStringException("Select the month Name");
        }
        List<String> list = Arrays.asList(monthList);
        if (!list.contains(monthName)){
            throw new HandleExpenseExceptionByMonth("Select the valid month");
        }
        Map<String, Long> graphData = new TreeMap<>();
        for (UserExpensePieChartByMonthdto userExpensePieChartByMonthdto:listOfExpense){
            graphData.put(userExpensePieChartByMonthdto.getExpenseType(),userExpensePieChartByMonthdto.getValue());
        }
        model.addAttribute("chartData",graphData);
         return "pie-chart";
    }

    @Override
    public String userExpensePieChartByYear(Integer userId, Integer year, Model model) {
        List<UserExpensePieChartByMonthdto> listOfExpense = userExpenseRepository.userExpensePieChartByYear(userId,year);
        Map<String,Long> graphData = new TreeMap<>();
        if (year<2000 || year> Year.now().getValue()){
            throw new HandleInvalidYearException("Year must be between 2000 and current year");
        }
        for (UserExpensePieChartByMonthdto userExpensePieChartByMonthdto:listOfExpense){
            graphData.put(userExpensePieChartByMonthdto.getExpenseType(),userExpensePieChartByMonthdto.getValue());
        }
        model.addAttribute("chartData",graphData);
        return "pie-chart-year";
    }
}
