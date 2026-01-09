package com.example.userexpense.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class TotalExpenseMonthRequestdto {
    String month;
    Integer year;
}
