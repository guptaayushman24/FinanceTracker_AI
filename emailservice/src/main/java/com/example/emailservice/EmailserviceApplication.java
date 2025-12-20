package com.example.emailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class EmailserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailserviceApplication.class, args);
	}

}
