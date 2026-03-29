package com.example.userexpense.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
public class AllExpenseMap {
    public Map<String,String> expenseMap(){
        HashMap<String,String> allExpenseMap = new HashMap<>();
        allExpenseMap.put("Health Care","Health_Care");
        allExpenseMap.put("Clothing","Clothing");
        allExpenseMap.put("Entertainment","Entertainment");
        allExpenseMap.put("Housing","Housing");
        allExpenseMap.put("Insurance","Insurance");
        allExpenseMap.put("Car Payment","Car_Payment");
        allExpenseMap.put("Cell Phone","Cell_Phone");
        allExpenseMap.put("Emergency Fund","Emergency_Fund");
        allExpenseMap.put("Personal Care","Personal_Care");
        allExpenseMap.put("Repairs","Repairs");
        allExpenseMap.put("Savings","Savings");
        allExpenseMap.put("Subscriptions","Subscriptions");
        allExpenseMap.put("Gym Memberships","Gym_Memberships");
        allExpenseMap.put("Home Maintenance","Home_Maintenance");
        allExpenseMap.put("Food","Food");
        allExpenseMap.put("Living Expenses","Living_Expenses");
        allExpenseMap.put("Gas","Gas");
        allExpenseMap.put("Miscellaneous Expenses","Miscellaneous_Expenses");
        allExpenseMap.put("Pets","Pets");

        return allExpenseMap;

    }
}
