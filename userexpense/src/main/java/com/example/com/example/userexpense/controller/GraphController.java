package com.example.userexpense.controller;

import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.UserExpensePieChartByMonthRequestdto;
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
    UserLoginId userLoginId;
    @GetMapping("/piechartbymonth")
    public String generatePieChart (@RequestParam String monthName, Model model){

         userExpensePieChartByMonthService.userExpensePieChartByMonth(userLoginId.getUserId(),monthName,model);
         return "pie-chart";
    }
}
