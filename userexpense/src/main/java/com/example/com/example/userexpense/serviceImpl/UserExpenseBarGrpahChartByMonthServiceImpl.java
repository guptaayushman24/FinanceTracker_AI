package com.example.userexpense.serviceImpl;

import com.example.userexpense.dto.BarGraphdto;
import com.example.userexpense.exception.HandleEmptyStringException;
import com.example.userexpense.exception.HandleExpenseExceptionByMonth;
import com.example.userexpense.exception.HandleInvalidYearException;
import com.example.userexpense.repository.UserExpenseRepository;
import com.example.userexpense.service.UserExpenseBarGrpahChartByMonthService;
import org.antlr.v4.runtime.tree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class UserExpenseBarGrpahChartByMonthServiceImpl implements UserExpenseBarGrpahChartByMonthService {
    @Autowired
    UserExpenseRepository userExpenseRepository;
    @Override
    public String userExpenseBarGraphtByMonth(Integer userId, String monthName, Model model,Integer year) {
        List<BarGraphdto> barGraphMonthData = userExpenseRepository.userExpenseBarGraphByMonth(userId,monthName,year);
        Map<String, Long> barGraphData = new TreeMap<>();
        for (BarGraphdto dto : barGraphMonthData) {
            barGraphData.put(dto.getPaymentMode(), dto.getCount());
        }
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
        List<String> list = Arrays.asList(monthList);
        if (!list.contains(monthName)){
            throw new HandleExpenseExceptionByMonth("Select the valid month name");
        }

        if (monthName.isEmpty()){
            throw new HandleEmptyStringException("Select the month Name");
        }
        model.addAttribute("barGraphData", barGraphData);
        return "bar-graph";
    }

    @Override
    public String userExpenseBarGraphByYear(Integer userId, Integer year, Model model) {
        List<BarGraphdto> barGraphYearData = userExpenseRepository.userExpenseBarGraphByYear(userId,year);
        Map<String,Long> barGraphData = new TreeMap<>();
        for (BarGraphdto dto:barGraphYearData){
            barGraphData.put(dto.getPaymentMode(),dto.getCount());
        }
        if (year<2000 || year> Year.now().getValue()){
            throw new HandleInvalidYearException("Year must be between 2000 and current year");
        }
        model.addAttribute("barGraphData",barGraphData);
        return "bar-graph-year";
    }
}
