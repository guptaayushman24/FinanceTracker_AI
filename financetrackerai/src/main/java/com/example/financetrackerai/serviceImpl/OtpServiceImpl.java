package com.example.financetrackerai.serviceImpl;

import com.example.financetrackerai.constants.CONSTANTS;
import com.example.financetrackerai.dto.*;
import com.example.financetrackerai.model.PasswordOTP;
import com.example.financetrackerai.repository.OTPRepository;
import com.example.financetrackerai.repository.UserRepository;
import com.example.financetrackerai.service.OtpService;
import com.example.financetrackerai.util.GenerateOTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OTPRepository otpRepository;

    @Autowired
    PasswordEncoder passwordEncoder;



    @Override
    public GenerateOTPResponseDTO generateOTPService(GenerateOTPRequestDTO generateOTPRequestDTO,Integer userId) {
        // generate 6 digit random otp and send it to the mail
        SimpleMailMessage sendOTP = new SimpleMailMessage();
        PasswordOTP passwordOTP = new PasswordOTP();
        GenerateOTPResponseDTO generateOTPResponseDTO = new GenerateOTPResponseDTO();

        int otp = GenerateOTP.generateOTP();
        String body = "Hii we receive your password reset request please enter " + otp + " " + "otp";
        sendOTP.setTo(generateOTPRequestDTO.getEmailAddress());
        sendOTP.setSubject(CONSTANTS.PASSWORD_RESET);
        sendOTP.setText(body);

        javaMailSender.send(sendOTP);

        // Save the otp in the database
        // int userId = userRepository.findByemailAddress(generateOTPRequestDTO.getEmailAddress()).getId();
        generateOTPResponseDTO.setMessage("OTP Send on Mail Successfully");
        passwordOTP.setUserId(userId);
        passwordOTP.setOtp(otp);
        passwordOTP.setCreatedAt(LocalDateTime.now());

        userRepository.save(passwordOTP);

        return generateOTPResponseDTO;


    }

    @Override
    public ValidateOTPResponseDTO validateOTP(ValidateOTPRequestDTO validateOtpRequestDTO,Integer userId) {
        OTPResponseData otpResponseData = otpRepository.findUserById(userId);
        ValidateOTPResponseDTO validateOTPResponseDTO = new ValidateOTPResponseDTO();

        if (otpResponseData.getCreatedAt()
                .plusMinutes(5)
                .isAfter(LocalDateTime.now()) && Objects.equals(otpResponseData.getOtp(), validateOtpRequestDTO.getOtp())){
                 validateOTPResponseDTO.setMessage("OTP Validated Successfully");
                 validateOTPResponseDTO.setStatus(1);

                 // Here update the password of the user
                return validateOTPResponseDTO;
        }
        else if (otpResponseData.getCreatedAt()
                .plusMinutes(5)
                .isAfter(LocalDateTime.now()) && !Objects.equals(otpResponseData.getOtp(), validateOtpRequestDTO.getOtp())){
            validateOTPResponseDTO.setMessage("Invalid OTP");
            validateOTPResponseDTO.setStatus(-1);

            return validateOTPResponseDTO;
        }


        validateOTPResponseDTO.setMessage("Please send the OTP again");
        validateOTPResponseDTO.setStatus(0);

         return validateOTPResponseDTO;
    }

    @Override
    public DeleteOTPResponsedto delteOTP(Integer userId) {
        DeleteOTPResponsedto deleteOTPResponsedto = new DeleteOTPResponsedto();
       int count = otpRepository.deleteUserOTP(userId);
        deleteOTPResponsedto.setStatus(1);
        deleteOTPResponsedto.setMessage("OTP Deleted Successfully");

        return deleteOTPResponsedto;
    }

    @Override
    public PasswordResponsedto resetPassword(Integer userId,String newPassword) {
        PasswordResponsedto passwordResetdto = new PasswordResponsedto();
        String encodedPassword = passwordEncoder.encode(newPassword);
        userRepository.updatePassword(userId,encodedPassword);
        passwordResetdto.setMessage("Password updated successfully");

        return passwordResetdto;
    }
}
