package com.example.financetrackerai.dto;

import jakarta.persistence.NamedStoredProcedureQueries;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateOTPResponseDTO {
    private String message;
}
