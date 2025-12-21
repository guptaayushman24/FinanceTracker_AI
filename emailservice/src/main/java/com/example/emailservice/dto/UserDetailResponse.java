package com.example.emailservice.dto;

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
public class UserDetailResponse {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private List<String> userExpense;

    @Override
    public String toString(){
        return firstName + "," + lastName + "," + emailAddress + "," + userExpense;
    }

}