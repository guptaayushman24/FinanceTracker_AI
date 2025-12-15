package com.example.financetrackerai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponsedto {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private List<String> userExpense;

    @Override
    public String toString(){
        return firstName + "," + lastName + "," + emailAddress + "," + userExpense;
    }
}
