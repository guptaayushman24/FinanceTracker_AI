package com.example.userexpensechatbot.controller;

import com.example.userexpensechatbot.service.EmbeddingService;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmbeddingController {
    @Autowired
    EmbeddingService embeddingService;
    @PostMapping("/embeddings")
    public ResponseEntity<float[]> getEmbeddings (@RequestBody String text){
       float []  response = embeddingService.getEmbedding(text);

       return ResponseEntity.ok(response);
    }
}
