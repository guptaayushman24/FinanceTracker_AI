package com.example.emailservice.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {
   String firstName;
   String lastName;
   String emailAddress;
   List<String> userExpenses;
}
