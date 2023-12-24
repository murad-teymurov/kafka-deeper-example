package com.company.kafkaconsumer.consumer;

import com.company.kafkaconsumer.exception.BusinessException;
import com.company.kafkaconsumer.exception.ValidationException;
import com.company.kafkaconsumer.model.OrderEvent;
import com.company.kafkaconsumer.publisher.KafkaPublisher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class Consumer {

    private final KafkaPublisher kafkaPublisher;

    private static final String TOPIC_NAME = "order-create";
    private static final String RETRY_TOPIC_NAME = "order-create.kafkaconsumer.retry";
    private static final String ERROR_TOPIC_NAME = "order-create.kafkaconsumer.error";
    private static final String GROUP_ID = "KafkaOrderConsumer-GroupId";
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = {TOPIC_NAME}, groupId = GROUP_ID, containerFactory = "kafkaListenerContainerFactory")
    public void listener(@Payload OrderEvent event, ConsumerRecord c) throws  Exception {
        try {
            consume(event);
        } catch (BusinessException | ValidationException exception) {
        } catch (ConnectException connectException) {
            String value = (String) c.value();
            JsonNode jsonNode = objectMapper.readTree(value);
            kafkaPublisher.publish(RETRY_TOPIC_NAME, jsonNode);
        }
    }

    @KafkaListener(topics = RETRY_TOPIC_NAME, groupId = GROUP_ID, containerFactory = "kafkaListenerContainerFactory")
    public void listener2(@Payload OrderEvent event, ConsumerRecord c) throws Exception {
        try {
            consume(event);
        } catch (BusinessException | ValidationException exception) {
        } catch (ConnectException connectException) {
            String value = (String) c.value();
            JsonNode jsonNode = objectMapper.readTree(value);
            kafkaPublisher.publish(ERROR_TOPIC_NAME, jsonNode);
        }
    }


    private void consume(OrderEvent orderEvent) throws ConnectException {
        int error = ThreadLocalRandom.current().nextInt(0,10);
        if(error < 7) {
            System.out.println(" I consumed.");
        }else if(error == 7) {
            throw new BusinessException("BusinessException.");
        } else if(error == 8) {
            throw  new ValidationException("ValidationException");
        } else  {
            throw new ConnectException("Coonect to mssql is failed. ");
        }
    }
}
