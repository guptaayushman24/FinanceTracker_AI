package com.example.userexpense.serviceImpl;

import com.example.userexpense.dto.UserExpensePieChartByMonthdto;
import com.example.userexpense.repository.UserExpenseRepository;
import com.example.userexpense.service.UserExpensePieChartByMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class UserExpensePieChartByMonthServiceImpl implements UserExpensePieChartByMonthService {
    @Autowired
    UserExpenseRepository userExpenseRepository;
    @Override
    public void userExpensePieChartByMonth(Integer userId, String monthName,Model model) {
        List<UserExpensePieChartByMonthdto> listOfExpense = userExpenseRepository.userExpensePieChartByMonth(userId,monthName);
        Map<String, Long> graphData = new TreeMap<>();
        for (UserExpensePieChartByMonthdto userExpensePieChartByMonthdto:listOfExpense){
            graphData.put(userExpensePieChartByMonthdto.getExpenseType(),userExpensePieChartByMonthdto.getValue());
        }
        model.addAttribute("chartData",graphData);
    }
}
