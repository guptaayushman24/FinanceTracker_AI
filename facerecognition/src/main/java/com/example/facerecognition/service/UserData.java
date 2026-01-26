package com.example.facerecognition.service;

import com.example.facerecognition.dto.LoginResponsedto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Vector;

public interface UserData {
    String saveUserDetail (String emailAddress, float [] imageEmbedding);
    LoginResponsedto faceAuthentication (MultipartFile image) throws IOException;
}
