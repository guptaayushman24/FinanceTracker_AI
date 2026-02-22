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
    public ResponseEntity<GenerateOTPResponseDTO> generateOTP(@RequestBody GenerateOTPRequestDTO generateOTPRequestDTO,@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
        return ResponseEntity.ok(otpService.generateOTPService(generateOTPRequestDTO,userId));
    }

    @PostMapping("/validateotp")
    public ResponseEntity<ValidateOTPResponseDTO> validateOtp(@RequestBody ValidateOTPRequestDTO validateOtpRequestDTO,
                                                              @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();
        ValidateOTPResponseDTO validateOTPResponseDTO = new ValidateOTPResponseDTO();
        validateOTPResponseDTO = otpService.validateOTP(validateOtpRequestDTO,userId);

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

    @GetMapping("/deleteotp")
    public ResponseEntity<DeleteOTPResponsedto> deleteOtp(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();

        return ResponseEntity.ok(otpService.delteOTP(userId));

    }

    @GetMapping("/resetpassword")
    public ResponseEntity<PasswordResponsedto> resetPassword (@RequestBody PasswordResetRequestdto passwordResetRequestdto, @RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Integer userId = extractUserId.getUserIdFromToken(token).intValue();

        return null;
    }
}
