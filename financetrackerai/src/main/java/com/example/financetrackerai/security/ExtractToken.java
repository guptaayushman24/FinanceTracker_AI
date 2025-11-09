package com.example.financetrackerai.security;

import com.example.financetrackerai.model.UserModel;
import com.example.financetrackerai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExtractToken {
    @Autowired
    AuthUtil authUtil;
    @Autowired
    UserRepository userRepository;

    public int extractUserId (String authHeader){
        try{
            if (authHeader==null || !authHeader.startsWith("Bearer ")){
                throw new Exception();
            }
            String token = authHeader.replace("Bearer","").trim();
            String email = authUtil.getUsernameFromToken(token);

            UserModel user = userRepository.findByemailAddress(email);
            if (user!=null){
                return user.getId();
            }
            else{
                throw new Exception();
            }
        }
        catch(Exception e){

        }
        return -1;
    }
}
