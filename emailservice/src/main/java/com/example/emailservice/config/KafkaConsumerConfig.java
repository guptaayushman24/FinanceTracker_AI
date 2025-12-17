//package com.example.emailservice.config;
//
//import com.example.emailservice.dto.UserDetailResponse;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import tools.jackson.databind.deser.jdk.StringDeserializer;
//
//public class KafkaConsumerConfig {
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, UserDetailResponse>
//    userDetailsKafkaListenerFactory() {
//
//        ConcurrentKafkaListenerContainerFactory<String, UserDetailResponse> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//
//        factory.setConsumerFactory(
//                new DefaultKafkaConsumerFactory<>(
//                        kafkaProperties.buildConsumerProperties(),
//                        new StringDeserializer(),
//                        new JsonDeserializer<>(UserDetailResponse.class)
//                )
//        );
//
//        return factory;
//    }
//}
