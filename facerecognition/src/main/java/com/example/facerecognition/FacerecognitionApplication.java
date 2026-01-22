package com.example.facerecognition;

import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class FacerecognitionApplication {

	public static void main(String[] args) {
        OpenCV.loadShared();
		SpringApplication.run(FacerecognitionApplication.class, args);
	}

}

// opt/homebrew/Cellar/opencv
