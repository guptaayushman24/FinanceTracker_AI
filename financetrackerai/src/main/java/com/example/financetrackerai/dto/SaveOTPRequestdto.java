package com.example.financetrackerai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveOTPRequestdto {
    private Integer userId;
    private Integer otp;
    private LocalDateTime createdAt;
}
