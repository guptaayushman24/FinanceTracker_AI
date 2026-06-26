package com.example.emailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDetailSchedulerdto {
    private Long totalPayment;
    private Long totalPaymentByCash;
    private Long totalPaymentByUpi;
    private Long percentageByCash;
    private Long parcentageByUpi;
    private Long schedulerDuration;
    private String emailAddress;
}
