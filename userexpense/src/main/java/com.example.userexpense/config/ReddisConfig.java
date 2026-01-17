package com.example.userexpense.config;
import com.example.userexpense.dto.UserExpenseResponsedto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;


@Configuration
@EnableCaching
public class ReddisConfig {
    @Bean
    public RedisTemplate<Integer, List<UserExpenseResponsedto>> redisTemplate (RedisConnectionFactory connectionFactory, ObjectMapper objectMapper){
        RedisTemplate<Integer,List<UserExpenseResponsedto>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}




