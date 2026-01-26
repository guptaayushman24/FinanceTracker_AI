package com.example.facerecognition.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponsedto {
        private String firstName;
        private String lastName;
        private String emailAddress;
        private List<String> userExpense;

        @Override
        public String toString(){
            return firstName + "," + lastName + "," + emailAddress + "," + userExpense;
        }
    }
