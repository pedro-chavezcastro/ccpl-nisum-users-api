package com.nisum.ccplnisumusersapi.config;

import com.nisum.ccplnisumusersapi.model.kafka.UserCreatedDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public KafkaTemplate<String, UserCreatedDto> kafkaTemplate() {
        DefaultKafkaProducerFactory<String, UserCreatedDto> kafkaProducerFactory =
                new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
        return new KafkaTemplate<>(kafkaProducerFactory);
    }

    @Bean
    public ConsumerFactory<String, UserCreatedDto> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserCreatedDto> listenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserCreatedDto> kafkaListenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
        return kafkaListenerContainerFactory;
    }

}