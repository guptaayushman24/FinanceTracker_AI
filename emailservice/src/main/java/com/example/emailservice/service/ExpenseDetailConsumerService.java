package com.example.emailservice.service;

import com.example.emailservice.constants.CONSTANTS;
import com.example.emailservice.dto.ExpenseDetailSchedulerdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ExpenseDetailConsumerService {
    @Autowired
    EmailService emailService;
    @KafkaListener(topics = "t.user.expesne.details",groupId = "user-expense",containerFactory = "expenseDetailSchedulerContainerFactory")
    public void consumeUserExpenseDetail(ExpenseDetailSchedulerdto expenseDetailSchedulerdto){
        if (expenseDetailSchedulerdto.getSchedulerDuration()==1){
            emailService.sendExpenseDetailMail(expenseDetailSchedulerdto.getTotalPayment(),expenseDetailSchedulerdto.getTotalPaymentByCash(),expenseDetailSchedulerdto.getTotalPaymentByUpi(),expenseDetailSchedulerdto.getParcentageByUpi(),expenseDetailSchedulerdto.getParcentageByUpi(),expenseDetailSchedulerdto.getEmailAddress(), CONSTANTS.WEAKLY_MAIL_SUBJECT,CONSTANTS.WEAKLY,CONSTANTS.WEAK_CONSTANT);
        }
        else if (expenseDetailSchedulerdto.getSchedulerDuration()==2){
            // Call the monthly mail
            emailService.sendExpenseDetailMail(expenseDetailSchedulerdto.getTotalPayment(),expenseDetailSchedulerdto.getTotalPaymentByCash(),expenseDetailSchedulerdto.getTotalPaymentByUpi(),expenseDetailSchedulerdto.getParcentageByUpi(),expenseDetailSchedulerdto.getParcentageByUpi(),expenseDetailSchedulerdto.getEmailAddress(), CONSTANTS.MONTHLY_MAIL_SUBJECT,CONSTANTS.MONTHLY,CONSTANTS.MONTH_CONSTANT);

        }

        else if  (expenseDetailSchedulerdto.getSchedulerDuration()==3){
            // Call the yearly mail
            emailService.sendExpenseDetailMail(expenseDetailSchedulerdto.getTotalPayment(),expenseDetailSchedulerdto.getTotalPaymentByCash(),expenseDetailSchedulerdto.getTotalPaymentByUpi(),expenseDetailSchedulerdto.getParcentageByUpi(),expenseDetailSchedulerdto.getParcentageByUpi(),expenseDetailSchedulerdto.getEmailAddress(), CONSTANTS.YEARLY_MAIL_SUBJECT,CONSTANTS.YEARLY,CONSTANTS.YEAR_CONSTANT);
        }
    }
}

