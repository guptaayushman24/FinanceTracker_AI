package com.example.financetrackerai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class StoreSignupData {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private List<String> userExpense;
}
