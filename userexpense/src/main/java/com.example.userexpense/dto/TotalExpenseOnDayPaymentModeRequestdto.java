package com.example.userexpense.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalExpenseOnDayPaymentModeRequestdto {
    private LocalDate expenseDate;
    private String paymentMode;
    private String isDate;
}
