package com.example.facerecognition;

import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FacerecognitionApplication {

	public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		SpringApplication.run(FacerecognitionApplication.class, args);
	}

}

// opt/homebrew/Cellar/opencv
