package com.example.facerecognition.repository;

import com.example.facerecognition.dto.SaveUserFaceDetailRequest;
import com.example.facerecognition.dto.SaveUserFaceDetailResponse;
import com.example.facerecognition.model.FaceEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Vector;


@Repository
public interface UserDataRepository extends JpaRepository<FaceEmbedding, Integer> {

    SaveUserFaceDetailResponse save(SaveUserFaceDetailRequest saveUserFaceDetailRequest);

//    @Query(nativeQuery = true,
//        value = "SELECT id FROM faceembedding <=> AS distance from vectorembedding:vectorembedding")
//        Integer userId = findUser (@Param("vectorembedding") float [] vectorembedding);
//    )

    @Query(value = """  
            SELECT id
                FROM faceembedding
                WHERE (vectorembedding <=> CAST(:vectorembedding AS vector)) <= 0.40
                ORDER BY vectorembedding <=> CAST(:vectorembedding AS vector)
                LIMIT 1
            """,nativeQuery = true)
    Integer findSimilarUser(@Param("vectorembedding") String pgVector);

}


