package com.example.expensechatbot.config;

import com.example.expensechatbot.chatbotservice.ChatbotAssistant;
import com.example.expensechatbot.chatbotserviceimpl.AskExpensedetailImpl;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatbotConfig {

    @Value("${ollama.base-url:http://localhost:11434}")
    private String ollamaBaseUrl;

    @Value("${ollama.model-name:qwen2.5:7b}")
    private String ollamaModelName;

    @Bean
    public ChatbotAssistant chatbotAssistant(AskExpensedetailImpl tool) {
        OllamaChatModel model = OllamaChatModel.builder()
                .baseUrl(ollamaBaseUrl)
                .modelName(ollamaModelName)
                .build();

        return AiServices.builder(ChatbotAssistant.class)
                .chatLanguageModel(model)
                .tools(tool)
                .build();
    }
}