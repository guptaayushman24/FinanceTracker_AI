package com.example.userexpense.controller;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.UserExpensePieChartByMonthRequestdto;
import com.example.userexpense.security.ExtractUserId;
import com.example.userexpense.service.UserExpensePieChartByMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GraphController {
    @Autowired
    UserExpensePieChartByMonthService userExpensePieChartByMonthService;
    @Autowired
    ExtractUserId extractUserId;
    @GetMapping("/piechartbymonth")
    public String generatePieChart (@RequestParam String monthName, Model model,@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
         userExpensePieChartByMonthService.userExpensePieChartByMonth(userId,monthName,model);
         return "pie-chart";
    }

    @GetMapping("/piechartbyyear")
    public String generatePieChartByYear (@RequestParam Integer year,Model model,@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
        userExpensePieChartByMonthService.userExpensePieChartByYear(userId,year,model);
        return "pie-chart-year";
    }
}
