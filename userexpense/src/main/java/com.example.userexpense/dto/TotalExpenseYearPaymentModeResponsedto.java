package com.example.userexpense.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalExpenseYearPaymentModeResponsedto {
    Long sum;
    String message;

    public TotalExpenseYearPaymentModeResponsedto(Long sum){
        this.sum = sum;
    }
}
