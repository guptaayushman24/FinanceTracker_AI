package com.example.financetrackerai.security;


import com.example.financetrackerai.model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;


import java.nio.charset.StandardCharsets;
import java.util.Date;

import io.jsonwebtoken.security.Keys;
@Component
public class AuthUtil {
    @Value("${jwt.secretKey}")
    private String jwtsecretkey;


    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtsecretkey.getBytes(StandardCharsets.UTF_8));
    }

    public String generationAccessToken (UserModel user) {
        return Jwts.builder()
                .setSubject(user.getEmailAddress())  // <-- use setSubject
                .claim("userId",user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(getSecretKey()) // replace with proper key
                .compact();
    }

    public String getUsernameFromToken (String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
