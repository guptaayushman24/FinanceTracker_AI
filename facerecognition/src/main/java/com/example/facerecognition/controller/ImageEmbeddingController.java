package com.example.facerecognition.controller;

import com.example.facerecognition.dto.*;
import com.example.facerecognition.service.ImageService;
// import org.springframework.ai.embedding.EmbeddingResponse;
import com.example.facerecognition.serviceImpl.UserDataImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Vector;

@RestController
public class ImageEmbeddingController {
    @Autowired
    ImageService imageService;

    @Autowired
    StoreUserSignupResponsedto storeUserSignupDatadto;

    @Autowired
    UserDataImpl userDataImpl;

    @PostMapping("/imageembedding")
    public float [] imageEmbedding(@RequestBody MultipartFile image) throws IOException {
        // float[] imageEmbedding = imageService.imageEmbeddingVecotor(image);
        float [] imageEmbedding = imageService.imageEmbeddingVecotor(image);
        storeUserSignupDatadto.setImageEmbedding(imageEmbedding);
        return imageEmbedding;
    }

    @PostMapping("/saveUserImage")
    public ResponseEntity<String> saveUserImage(@RequestBody UserEmailRequestdto userEmailRequestdto) {
        userDataImpl.saveUserDetail(userEmailRequestdto.getEmailAddress(), storeUserSignupDatadto.getImageEmbedding());
        return ResponseEntity.ok("Face Registered Successfully");
    }

    @PostMapping("/faceauthentication")
     public ResponseEntity<LoginResponsedto> faceLogin (@RequestBody MultipartFile image) throws IOException {
        return ResponseEntity.ok(userDataImpl.faceAuthentication(image));

     }

}
