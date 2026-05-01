package com.example.expensechatbot.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SumOfExpense {
    private Long sum;
    public SumOfExpense(Long sum) {
        this.sum = sum;
    }
}
