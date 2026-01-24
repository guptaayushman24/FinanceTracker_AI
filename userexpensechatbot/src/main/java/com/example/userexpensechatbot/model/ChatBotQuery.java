package com.example.userexpensechatbot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ChatBotQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String query;
    private float [] vectorEmbedding;
    private String JPAQuery;
}
