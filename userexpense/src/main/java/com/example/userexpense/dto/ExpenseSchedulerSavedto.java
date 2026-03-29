package com.example.userexpense.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseSchedulerSavedto {
    Integer id;
    Integer user_id;
    String days;
}
