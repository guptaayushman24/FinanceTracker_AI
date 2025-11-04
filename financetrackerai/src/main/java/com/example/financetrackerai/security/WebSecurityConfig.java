package com.example.financetrackerai.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http,JWTAuthenticationFilter jwtAuthenticationFilter){

    }

}
