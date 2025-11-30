package com.example.userexpense.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllExpenseeResponsedto {
    private String expenseType;
    private Integer value;
    private String description;
    private String paymentMode;
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM--dd")

    private LocalDate expense_date;




}
