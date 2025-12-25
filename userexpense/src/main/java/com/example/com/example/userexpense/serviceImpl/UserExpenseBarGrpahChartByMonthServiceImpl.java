package com.example.userexpense.serviceImpl;

import com.example.userexpense.dto.BarGraphdto;
import com.example.userexpense.repository.UserExpenseRepository;
import com.example.userexpense.service.UserExpenseBarGrpahChartByMonthService;
import org.antlr.v4.runtime.tree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class UserExpenseBarGrpahChartByMonthServiceImpl implements UserExpenseBarGrpahChartByMonthService {
    @Autowired
    UserExpenseRepository userExpenseRepository;
    @Override
    public String userExpenseBarGraphtByMonth(Integer userId, String monthName, Model model) {
        List<BarGraphdto> barGraphMonthData = userExpenseRepository.userExpenseBarGraphByMonth(userId,monthName);
        Map<String, Long> barGraphData = new TreeMap<>();
        for (BarGraphdto dto : barGraphMonthData) {
            barGraphData.put(dto.getPaymentMode(), dto.getCount());
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
        model.addAttribute("barGraphData",barGraphData);
        return "bar-graph-year";
    }
}
