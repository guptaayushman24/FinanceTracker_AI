package com.example.financetrackerai.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Integer userId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private List<String> userExpense;
    @JsonIgnore
    private String userPassword;

    @Override
    public String toString(){
        return firstName + "," + lastName + "," + emailAddress + "," + userExpense;
    }
}
