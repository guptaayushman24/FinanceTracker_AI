package com.example.emailservice.config;

import com.example.emailservice.dto.UserDetailResponse;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, UserDetailResponse> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
       //  config.put(ConsumerConfig.GROUP_ID_CONFIG, "user-details");
        config.put(ConsumerConfig.GROUP_ID_CONFIG,"user-details-group");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        JacksonJsonDeserializer<UserDetailResponse> valueDeserializer =
                new JacksonJsonDeserializer<>(UserDetailResponse.class);
        valueDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                valueDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserDetailResponse> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserDetailResponse> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}