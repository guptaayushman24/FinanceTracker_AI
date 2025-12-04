package com.example.userexpense.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalExpenseYearResponsedto {
    Long sum;
    String message;

    public TotalExpenseYearResponsedto(Long sum){
        this.sum = sum;
    }
}
