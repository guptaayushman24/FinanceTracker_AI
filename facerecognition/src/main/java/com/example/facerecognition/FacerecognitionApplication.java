package com.example.facerecognition;

import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.example")
public class FacerecognitionApplication {

	public static void main(String[] args) {
        OpenCV.loadShared();
		SpringApplication.run(FacerecognitionApplication.class, args);
	}

}
