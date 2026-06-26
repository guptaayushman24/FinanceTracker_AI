package com.example.userexpense.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TotalExpenseWeekResponsedto {
    Long sum;
    String message;

    public TotalExpenseWeekResponsedto(Long sum){
        this.sum = sum;
    }
}