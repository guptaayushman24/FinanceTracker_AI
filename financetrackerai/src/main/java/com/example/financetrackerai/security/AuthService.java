package com.example.financetrackerai.security;

import com.example.financetrackerai.config.StoreUserId;
import com.example.financetrackerai.dto.LoginRequestdto;
import com.example.financetrackerai.dto.LoginResponsedto;
import com.example.financetrackerai.model.UserModel;
import com.example.financetrackerai.producer.Producer;
import com.example.financetrackerai.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    Producer producer;
    @Autowired
    StoreUserId storeUserId;
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<LoginResponsedto> login (LoginRequestdto loginRequestdto) {
        try {
            if (loginRequestdto.getEmail() == null || loginRequestdto.getPassword() == null) {
                throw new Exception("Field are missing");
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestdto.getEmail(), loginRequestdto.getPassword())
            );
            UserModel userModel = (UserModel) authentication.getPrincipal();
            String token = authUtil.generationAccessToken(userModel);

            // Kafka Produer logic
            System.out.println("User id is" + " " + userModel.getId());
            if (userModel.getId() != null) {
                String email = loginRequestdto.getEmail();
                int getUserId = userRepository.findByemailAddress(email).getId();
                System.out.println("Produced user id is"+" "+String.valueOf(getUserId));
                producer.sendMessage(String.valueOf(getUserId));
            }
            return ResponseEntity.ok(new LoginResponsedto(userModel.getId(), userModel.getEmailAddress(), token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponsedto());
        }
    }
}
