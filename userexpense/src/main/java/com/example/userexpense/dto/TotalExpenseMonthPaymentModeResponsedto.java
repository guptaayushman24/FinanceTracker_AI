package com.example.userexpense.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalExpenseMonthPaymentModeResponsedto {
    Long sum;
    String message;

    public TotalExpenseMonthPaymentModeResponsedto (Long sum){
        this.sum = sum;
    }
}
