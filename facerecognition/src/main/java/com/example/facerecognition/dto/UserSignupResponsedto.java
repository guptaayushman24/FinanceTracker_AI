package com.example.facerecognition.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupResponsedto {
    private String userId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
}
