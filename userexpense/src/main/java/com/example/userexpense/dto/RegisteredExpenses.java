package com.example.userexpense.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RegisteredExpenses {
    private String Health_Care;
    private String Clothing;
    private String Entertainment;
    private String Housing;
    private String Insurance;
    private String Car_Payment;
    private String Cell_Phone;
    private String Emergency_Fund;
    private String Personal_Care;
    private String Repairs;
    private String Savings;
    private String Subscriptions;
    private String Gym_Memberships;
    private String Home_Maintenance;
    private String Food;
    private String Living_Expenses;
    private String Gas;
    private String Miscellaneous_Expenses;
    private String Pets;
}
