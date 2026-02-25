package com.example.financetrackerai.controller;

import com.example.financetrackerai.config.ExtractUserId;
import com.example.financetrackerai.dto.*;
import com.example.financetrackerai.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OtpController {
    @Autowired
    OtpService otpService;

    @Autowired
    ExtractUserId extractUserId;

    @PostMapping("/generateotp")
    public ResponseEntity<GenerateOTPResponseDTO> generateOTP(@RequestBody GenerateOTPRequestDTO generateOTPRequestDTO) {
        return ResponseEntity.ok(otpService.generateOTPService(generateOTPRequestDTO));
    }

    @PostMapping("/validateotp")
    public ResponseEntity<ValidateOTPResponseDTO> validateOtp(@RequestBody ValidateOTPRequestDTO validateOtpRequestDTO) {
        ValidateOTPResponseDTO validateOTPResponseDTO = new ValidateOTPResponseDTO();
        validateOTPResponseDTO = otpService.validateOTP(validateOtpRequestDTO);

        if (validateOTPResponseDTO.getStatus() == 1) {
            validateOTPResponseDTO.setMessage("OTP Validated Successfully and Password Reset Successfully");
            validateOTPResponseDTO.setStatus(1);

            return ResponseEntity.ok(validateOTPResponseDTO);
        } else if (validateOTPResponseDTO.getStatus() == 0) {
            validateOTPResponseDTO.setMessage("Invalid OTP");
            validateOTPResponseDTO.setStatus(-1);

            return ResponseEntity.ok(validateOTPResponseDTO);
        }

        validateOTPResponseDTO.setMessage("Please send the OTP again");
        validateOTPResponseDTO.setStatus(0);

        return ResponseEntity.ok(validateOTPResponseDTO);

    }

    @PostMapping("/deleteotp")
    public ResponseEntity<DeleteOTPResponsedto> deleteOtp(@RequestBody DeleteOTPRequestdto deleteOTPRequestdto){

        return ResponseEntity.ok(otpService.delteOTP(deleteOTPRequestdto.getEmailAddress()));

    }

    @PostMapping("/resetpassword")
    public ResponseEntity<PasswordResponsedto> resetPassword (@RequestBody PasswordResetRequestdto passwordResetRequestdto){
        // Take the emailAddress in the request and update it
        return null;
    }
}
