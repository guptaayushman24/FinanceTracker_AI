package com.example.facerecognition.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Vector;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreUserSignupResponsedto {
    private Integer userId;
    private String fistName;
    private String lastName;
    private String emailAddress;
    private String password;
     private float [] imageEmbedding;
}
