package com.example.facerecognition.serviceImpl;

import com.example.facerecognition.dto.*;
import com.example.facerecognition.model.FaceEmbedding;
import com.example.facerecognition.repository.UserDataRepository;
import com.example.facerecognition.service.ImageService;
import com.example.facerecognition.service.UserData;
import com.example.financetrackerai.model.UserModel;
import com.example.financetrackerai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserDataImpl implements UserData {

    @Autowired
    UserDataRepository userDataRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageService imageService;
    @Override
    public String saveUserDetail(String emailAddress,float [] imageEmbedding) {
        try{
            // Here call the repository method of the finance tracker service fetch the userId
            //  Consume the embedding of the image
            // Save in the SaveUserFaceDetailResponsedto
            // Pass both the DTO in the save method of the facerecognition method
            FaceEmbedding faceEmbedding = new FaceEmbedding();
            UserModel userModel = userRepository.findByemailAddress(emailAddress);



            faceEmbedding.setUserId(userModel.getId());
            faceEmbedding.setVectorEmbedding(imageEmbedding); // pass the embedding vector
            userDataRepository.save(faceEmbedding);

            return "User Face Registered Successfully";
        } catch (Exception e) {
            System.out.println("Exception :::::"+" "+e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoginResponsedto faceAuthentication(MultipartFile image) throws IOException {
        LoginResponsedto loginResponsedto = new LoginResponsedto();
        float [] imageEmbedding = imageService.imageEmbeddingVecotor(image);
        String pgVector = toPgVector (imageEmbedding);
        Similaritydto similarity = userDataRepository.findSimilarUser(pgVector);

        loginResponsedto.setEmail(null);
        System.out.println("Similarity"+" "+similarity.getSimilarity());
        if (similarity.getSimilarity()>=0.40) {
            similarity.setUserId(similarity.getUserId());
            loginResponsedto.setId(similarity.getUserId());
        }


        loginResponsedto.setJwt(null);

        return loginResponsedto;
    }

    private String toPgVector(float[] imageEmbedding) {
        return "[" +
                IntStream.range(0, imageEmbedding.length)
                        .mapToObj(i -> String.valueOf(imageEmbedding[i]))
                        .collect(Collectors.joining(","))
                + "]";
    }
}
