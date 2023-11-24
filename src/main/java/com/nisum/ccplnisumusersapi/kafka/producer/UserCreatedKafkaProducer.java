package com.nisum.ccplnisumusersapi.kafka.producer;

import com.nisum.ccplnisumusersapi.model.kafka.UserCreatedDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Component
public class UserCreatedKafkaProducer {

    @Autowired
    private KafkaTemplate<String, UserCreatedDto> kafkaTemplate;

    @Value("${spring.kafka.producer.topic-name}")
    private String topicName;

    public void produce(UserCreatedDto user) {
        ListenableFuture<SendResult<String, UserCreatedDto>> listenableFuture = kafkaTemplate.send(topicName, user);
        sendCallback(listenableFuture);
    }

    private static void sendCallback(ListenableFuture<SendResult<String, UserCreatedDto>> listenableFuture) {
        listenableFuture.addCallback(new KafkaSendCallback<>() {

            @Override
            public void onSuccess(SendResult<String, UserCreatedDto> result) {
                log.info("Message sent: {}", result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(KafkaProducerException e) {
                log.info("Error sending message: {}", e.getMessage());
            }
        });
    }

}