package com.nisum.ccplnisumusersapi.kafka.consumer;

import com.nisum.ccplnisumusersapi.model.kafka.UserCreatedDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserCreatedKafkaConsumer {

    @Autowired
    private KafkaListenerEndpointRegistry listenerEndpointRegistry;

    @KafkaListener(
            id = "user-created-listener",
            autoStartup = "true",
            topics = "${spring.kafka.consumer.topic-name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void listener(UserCreatedDto message) {
        log.info("Mensaje leido del topic {}", message.getName());
        // listenerEndpointRegistry.getListenerContainer("user-created-listener").start();
        // listenerEndpointRegistry.getListenerContainer("user-created-listener").stop();
    }

}
