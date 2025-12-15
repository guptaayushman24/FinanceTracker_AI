package com.example.emailservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {
    String firstName;
    String lastName;
    String emailAddress;
    List<String> userExpenses;
}