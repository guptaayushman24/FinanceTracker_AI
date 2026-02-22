package com.example.financetrackerai.service;

import com.example.financetrackerai.dto.*;


public interface OtpService {
    public GenerateOTPResponseDTO generateOTPService (GenerateOTPRequestDTO generateOTPRequestDTO,Integer userId);
    public ValidateOTPResponseDTO validateOTP (ValidateOTPRequestDTO otpRequestDTO,Integer userId);
    public DeleteOTPResponsedto delteOTP (Integer userId);
    public PasswordResponsedto resetPassword (Integer userId,String newPassword);
}
