package com.example.userexpense.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.common.security.oauthbearer.internals.secured.ValidateException;
import org.springframework.cglib.core.Local;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserExpense {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private Integer user_id;
    private String ExpenseType;
    private Integer Value;
    private String Description;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate expenseDate;
    @ManyToOne
    @JoinColumn(name = "payment_mode_id")
    private PaymentMode paymentMode;

    public UserExpense (String ExpenseType, Integer Value, String Description, LocalDate expenseDate) {
        this.ExpenseType = ExpenseType;
        this.Value = Value;
        this.Description = Description;
        this.expenseDate = expenseDate;
    }
}
