package com.example.userexpense.controller;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.service.UserExpenseBarGrpahChartByMonthService;
import com.example.userexpense.service.UserExpensePieChartByMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BarGraphController {

    @Autowired
    UserExpenseBarGrpahChartByMonthService userExpenseBarGrpahChartByMonthService;
    @Autowired
    UserLoginId userLoginId;
    @GetMapping("/bargraphbymonth")
    public String generateBarGraphMonth (@RequestParam String monthName,Model model){

        userExpenseBarGrpahChartByMonthService.userExpenseBarGraphtByMonth(userLoginId.getUserId(),monthName,model);
        return "bar-graph";
    }

    @GetMapping("/bargraphbyyear")
    public String generateBarGraphYear (@RequestParam Integer year,Model model){
        userExpenseBarGrpahChartByMonthService.userExpenseBarGraphByYear(userLoginId.getUserId(),year,model);
        return "bar-graph-year";
    }
}
