package com.example.financetrackerai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableFeignClients
public class FinancetrackeraiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancetrackeraiApplication.class, args);
	}

}
