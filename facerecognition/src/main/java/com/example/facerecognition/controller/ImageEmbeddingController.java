package com.example.facerecognition.controller;

import com.example.facerecognition.service.ImageService;
// import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
public class ImageEmbeddingController {
    @Autowired
    ImageService imageService;

    @PostMapping("/imageembedding")
    public float[] imageEmbedding (@RequestBody MultipartFile image) throws IOException{
        return imageService.imageEmbeddingVecotor(image);
    }
}
