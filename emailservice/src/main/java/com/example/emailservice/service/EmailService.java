package com.example.emailservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import com.example.emailservice.constants.CONSTANTS;
@Service
@Slf4j
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

    public void sendExpenseDetailMail(Long totalPayment, Long totalPaymentByUpi, Long totalPaymentByCash,
                                      Long percentageByUpi, Long percentageByCash, String mailTo, String mailSubject, String mailConstant, Integer durationConstant) {
        String durationName = "";
        if (durationConstant==1){
            durationName = CONSTANTS.WEAKSMALL;
        }
        else if (durationConstant == 2) {
            durationName = CONSTANTS.MONTHSMALL;
        } else if (durationConstant == 3) {
            durationName = CONSTANTS.YEARSMALL;
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        String insight = generateInsight(percentageByUpi, percentageByCash, mailConstant);

        String body = """
        Hello,
        
        Hope you are doing well! Here is your %s Expense Summary for %s %d.
        
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        📊 %s EXPENSE REPORT
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        
        💰 Total Expense        :  ₹%d
        
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        📱 Payment Mode Breakdown
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        
        UPI Payments
          • Total Amount        :  ₹%d
          • Percentage of Spend :  %d%%
        
        Cash Payments
          • Total Amount        :  ₹%d
          • Percentage of Spend :  %d%%
        
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        💡 Quick Insight
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        
        %s
        
        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
        
        Keep tracking your expenses to stay on top of your finances!
        
        Regards,
        FinanceTracker AI Team
        """.formatted(
                mailConstant,        // "your %s Expense Summary"
                durationName,        // "for %s" (month/year name)
                durationConstant,    // "%d" (year number e.g. 2025)
                mailConstant,        // "%s EXPENSE REPORT"
                totalPayment,        // "₹%d Total Expense"
                totalPaymentByUpi,   // "₹%d UPI"
                percentageByUpi,     // "%d%% UPI percentage"
                totalPaymentByCash,  // "₹%d Cash"
                percentageByCash,    // "%d%% Cash percentage"
                insight              // "%s Quick Insight"
        );

        mailMessage.setTo(mailTo);
        mailMessage.setSubject(mailSubject);
        mailMessage.setText(body);
    }

    // generates a simple insight line based on payment pattern
    private String generateInsight(Long percentageByUpi, Long percentageByCash,String mailConstant) {
        long upi = percentageByUpi != null ? percentageByUpi : 0L;
        long cash = percentageByCash != null ? percentageByCash : 0L;

        if (upi > cash) {
            return "You mostly used UPI this " + mailConstant + " (" + upi + "%). " +
                    "Great choice for tracking digital payments!";
        } else if (cash > upi) {
            return "You mostly used Cash this " + mailConstant + " (" + cash + "%). " +
                    "Consider using UPI for better expense tracking!";
        } else {
            return "You used UPI and Cash equally this " + mailConstant + ". " +
                    "Great balance between digital and cash payments!";
        }
    }
    }
