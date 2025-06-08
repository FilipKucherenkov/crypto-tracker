package com.fnk.services;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaPartitionKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface MessageProducer {
    @Topic("coinbase-messages")
    void send(
            @KafkaKey @KafkaPartitionKey String key,
            byte[] coinbaseMessage
    );
}
