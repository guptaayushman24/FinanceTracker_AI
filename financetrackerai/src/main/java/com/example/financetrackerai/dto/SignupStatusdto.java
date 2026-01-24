package com.example.financetrackerai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupStatusdto {
    private Integer signUpStatus;
    private String emailAddress;
}
