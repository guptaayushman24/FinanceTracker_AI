package com.example.expensechatbot.responsedto;

import lombok.AllArgsConstructor;

import java.time.LocalDate;


public class ExpenseSummary {
    private String expenseType;
    private Integer amount;
    private String description;
    private LocalDate expenseDate;
    private String paymentMode;

    public ExpenseSummary (String expenseType,Integer amount,String description,LocalDate expenseDate){
        this.expenseType = expenseType;
        this.amount = amount;
        this.description = description;
        this.expenseDate = expenseDate;
    }

    public ExpenseSummary (String expenseType,Integer amount,String description,LocalDate expenseDate,String paymentMode){
        this.expenseType = expenseType;
        this.amount = amount;
        this.description = description;
        this.expenseDate = expenseDate;
        this.paymentMode = paymentMode;
    }
}
