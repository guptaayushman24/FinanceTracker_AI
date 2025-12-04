package com.example.userexpense.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalExpenseCurrentDayRequestdto {
    LocalDateTime current_date;
}
