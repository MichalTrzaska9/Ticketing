package com.example.ticketing.purchase.configuration;

import com.example.ticketing.purchase.event.PurchasePlacedEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;


@TestConfiguration
public class KafkaMockConfig {
    private static final String TEST_TOPIC = "purchase-placed";

    @Bean
    public KafkaTemplate<String, PurchasePlacedEvent> kafkaTemplate() {
        KafkaTemplate<String, PurchasePlacedEvent> mock = Mockito.mock(KafkaTemplate.class);

        RecordMetadata metadata = new RecordMetadata(
                new TopicPartition(TEST_TOPIC, 0),
                0, 0, System.currentTimeMillis(), 0L, 0, 0);

        PurchasePlacedEvent event = new PurchasePlacedEvent();
        event.setPurchaseNumber("test-purchase-123");

        ProducerRecord<String, PurchasePlacedEvent> record =
                new ProducerRecord<>(TEST_TOPIC, event);

        SendResult<String, PurchasePlacedEvent> sendResult =
                new SendResult<>(record, metadata);

        Mockito.when(mock.send(Mockito.any(), Mockito.any()))
                .thenReturn(CompletableFuture.completedFuture(sendResult));

        return mock;
    }
}
