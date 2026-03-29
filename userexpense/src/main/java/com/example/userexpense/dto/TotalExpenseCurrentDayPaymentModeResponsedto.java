package com.example.userexpense.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalExpenseCurrentDayPaymentModeResponsedto {
    Long sum;
    String message;

    public TotalExpenseCurrentDayPaymentModeResponsedto (Long sum){
        this.sum = sum;
    }
}
