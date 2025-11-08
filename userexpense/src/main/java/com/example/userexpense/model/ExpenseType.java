package com.example.userexpense.model;

import jakarta.persistence.*;
@Entity
public class ExpenseType {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String ExpenseType;
    private Integer Value;
    private String Description;
    @ElementCollection
    @CollectionTable(
            name = "mode_of_payment",
            joinColumns = @JoinColumn(name="expense_type_id")
    )
    private String Payment_method;

}
