package com.example.userexpense.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseAnalyzerResponsedto {
    String Description;
    String ExpenseType;
    Integer Value;
    String paymentMode;
}
