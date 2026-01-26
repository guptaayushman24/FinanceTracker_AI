package com.example.facerecognition.service;

// import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Vector;

public interface ImageService {
    // public EmbeddingResponse imageEmbedding (String... imageString) throws IOException;
    public float[]  imageEmbeddingVecotor (MultipartFile image) throws IOException;
}
