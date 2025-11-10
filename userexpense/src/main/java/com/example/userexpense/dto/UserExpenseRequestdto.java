package com.example.userexpense.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserExpenseRequestdto {
    private String expenseType;
    private Integer value;
    private String description;
    private String modeOfPayment;
}
