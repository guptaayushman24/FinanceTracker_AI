package com.example.userexpensechatbot.service;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class EmbeddingService {
    private final EmbeddingModel embeddingModel;
    public EmbeddingService (EmbeddingModel embeddingModel){
        this.embeddingModel = embeddingModel;
    }

    public float[] getEmbedding(String text){
//        EmbeddingRequest request = new
        return embeddingModel.embed(text);
//        return embeddingModel.call(request);
    }
}
