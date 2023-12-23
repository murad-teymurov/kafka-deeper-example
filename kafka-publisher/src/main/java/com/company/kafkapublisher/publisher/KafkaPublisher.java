package com.company.kafkapublisher.publisher;

import com.company.kafkapublisher.entity.Outbox;
import com.company.kafkapublisher.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final OutboxRepository outboxRepository;

    public void publish(String topicName, Object message) {
        try {
//            If(1 == 1) {
//                throw new RuntimeException();
//            }
            kafkaTemplate.send(topicName, message);
        } catch (Exception ex) {
            String key = UUID.randomUUID().toString();
            Outbox outbox = new Outbox(topicName, key, topicName, message);
            outboxRepository.save(outbox);
        }
    }
}
