package com.example.userexpense.controller;

//import com.example.userexpense.config.UserLoginId;
import com.example.userexpense.dto.UserExpensePieChartByMonthRequestdto;
import com.example.userexpense.security.ExtractUserId;
import com.example.userexpense.service.UserExpensePieChartByMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class GraphController {
    @Autowired
    UserExpensePieChartByMonthService userExpensePieChartByMonthService;
    @Autowired
    ExtractUserId extractUserId;
    @GetMapping("/piechartbymonth")
    public ResponseEntity<Map<String,Long>> generatePieChart (@RequestParam String monthName, @RequestParam Integer year, Model model, @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
        model.addAttribute("bearerToken",token);
        return ResponseEntity.ok(userExpensePieChartByMonthService.userExpensePieChartByMonth(userId,monthName,model,year));
    }


    @GetMapping("/piechartbyyear")
    public ResponseEntity<Map<String,Long>> generatePieChartByYear (@RequestParam Integer year,Model model,@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
        return ResponseEntity.ok(userExpensePieChartByMonthService.userExpensePieChartByYear(userId,year,model));
    }
}
