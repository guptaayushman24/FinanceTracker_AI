package com.example.userexpense.service;

import com.example.userexpense.constant.CONSTANTS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendMail (String userEmail,String body){
        SimpleMailMessage msg = new SimpleMailMessage();
        String subject = CONSTANTS.MAIL_SUBJECT;

        msg.setTo(userEmail);
        msg.setSubject(subject);
        msg.setText(body);
        javaMailSender.send(msg);

    }
}
