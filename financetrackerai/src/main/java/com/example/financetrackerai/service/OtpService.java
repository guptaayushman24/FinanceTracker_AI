package com.example.financetrackerai.service;

import com.example.financetrackerai.dto.*;


public interface OtpService {
    public GenerateOTPResponseDTO generateOTPService (GenerateOTPRequestDTO generateOTPRequestDTO);
    public ValidateOTPResponseDTO validateOTP (ValidateOTPRequestDTO otpRequestDTO);
    public DeleteOTPResponsedto delteOTP (String emailAddress);
    public PasswordResponsedto resetPassword (Integer userId,String newPassword);
}
