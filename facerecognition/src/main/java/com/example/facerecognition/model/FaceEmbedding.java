package com.example.facerecognition.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Vector;

@Entity
@Table(name = "faceembedding")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FaceEmbedding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private Integer userId;
   private float [] vectorEmbedding;
}
