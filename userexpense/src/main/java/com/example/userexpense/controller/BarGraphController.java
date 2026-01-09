package com.example.userexpense.controller;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.security.ExtractUserId;
import com.example.userexpense.service.UserExpenseBarGrpahChartByMonthService;
import com.example.userexpense.service.UserExpensePieChartByMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BarGraphController {

    @Autowired
    UserExpenseBarGrpahChartByMonthService userExpenseBarGrpahChartByMonthService;
    @Autowired
    ExtractUserId extractUserId;
    @GetMapping("/bargraphbymonth")
    public String generateBarGraphMonth (@RequestParam String monthName,Model model,@RequestHeader("Authorization") String authorizationHeader,Integer year){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
        userExpenseBarGrpahChartByMonthService.userExpenseBarGraphtByMonth(userId,monthName,model,year);
        return "bar-graph";
    }

    @GetMapping("/bargraphbyyear")
    public String generateBarGraphYear (@RequestParam Integer year,Model model,@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
        userExpenseBarGrpahChartByMonthService.userExpenseBarGraphByYear(userId,year,model);
        return "bar-graph-year";
    }
}
