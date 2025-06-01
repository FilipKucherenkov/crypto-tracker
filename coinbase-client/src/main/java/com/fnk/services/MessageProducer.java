package com.fnk.services;

import com.fnk.dto.coinbase.CoinbaseMessage;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface MessageProducer {
    @Topic("coinbase-message")
    void send(String key, CoinbaseMessage coinbaseMessage);
}
