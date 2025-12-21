package com.example.emailservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.emailservice.constants.CONSTANTS;
@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendMail (String firstName, String lastName, String userEmail, List<String> userExpense){
        SimpleMailMessage msg = new SimpleMailMessage();
         String subject = CONSTANTS.MAIL_SUBJECT;
        String body = "Hello" +" "+firstName+" "+lastName +" "+"Welcome to the finance tracker application you have registered for the following expenses" +" "+userExpense.toString()+" "+ "which you have registered at the time of the signup. You can add and delete the expense as per your requirements from the application\n" +
                "Thank You\n" +
                "Finance Tracker Team";

            msg.setTo(userEmail);
            msg.setSubject(subject);
            msg.setText(body);
        javaMailSender.send(msg);

    }
}
