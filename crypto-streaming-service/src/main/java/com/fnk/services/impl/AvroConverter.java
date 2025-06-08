package com.fnk.services.impl;

import com.fnk.dto.coinbase.CoinbaseMessage;
import com.fnk.dto.coinbase.HeartbeatMessage;
import com.fnk.dto.coinbase.HeartbeatMessageAvro;
import com.fnk.dto.coinbase.SubscriptionsMessage;
import com.fnk.dto.coinbase.SubscriptionsMessageAvro;
import com.fnk.dto.coinbase.TickerMessage;
import com.fnk.dto.coinbase.TickerMessageAvro;
import com.fnk.dto.coinbase.ChannelAvro;

import jakarta.inject.Singleton;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Singleton
public class AvroConverter {
    private final Logger LOGGER = LoggerFactory.getLogger(AvroConverter.class);

    /**
     * Serialize a coinbase message dto to avro format
     */
    public byte[] serializeToAvro(CoinbaseMessage coinbaseMessage) throws IOException {
        if (coinbaseMessage instanceof TickerMessage tickerMessage) {
            LOGGER.info("Received a ticker message: {}",  tickerMessage);
            TickerMessageAvro avroMessage = mapToAvro(tickerMessage);
            return encodeAvro(avroMessage);
        } else if(coinbaseMessage instanceof HeartbeatMessage heartbeatMessage) {
            LOGGER.info("Received a heartbeat message: {}",  heartbeatMessage);
            HeartbeatMessageAvro avroMessage = mapToAvro(heartbeatMessage);
            return encodeAvro(avroMessage);
        } else{
            LOGGER.info("Received a subscription message: {}", coinbaseMessage);
            SubscriptionsMessageAvro avroMessage = mapToAvro((SubscriptionsMessage) coinbaseMessage);
            return encodeAvro(avroMessage);
        }
    }

    /**
     * Map HeartbeatMessage to HeartbeatMessageAvro.
     */
    private HeartbeatMessageAvro mapToAvro(HeartbeatMessage message) {
        return HeartbeatMessageAvro.newBuilder()
                .setType(message.getType())
                .setSequence(message.getSequence())
                .setProductId(message.getProductId())
                .setTime(message.getTime().toString())
                .build();
    }

    /**
     * Map SubscriptionsMessage to SubscriptionsMessageAvro.
     */
    private SubscriptionsMessageAvro mapToAvro(SubscriptionsMessage message) {
        return SubscriptionsMessageAvro.newBuilder()
                .setChannels(message.getChannels().stream().map(this::mapToAvro).toList())
                .setType(message.getType())
                .build();
    }

    private ChannelAvro mapToAvro(SubscriptionsMessage.Channel channel){
        return ChannelAvro.newBuilder()
                .setName(channel.getName())
                .setAccountIds(channel.getAccountIds())
                .setProductIds(channel.getProductIds())
                .build();
    }

    /**
     * Map TickerMessage to TickerMessageAvro.
     */
    private TickerMessageAvro mapToAvro(TickerMessage message) {
        return TickerMessageAvro.newBuilder()
                .setType(message.getType())
                .setSequence(message.getSequence())
                .setProductId(message.getProductId())
                .setTime(message.getTime().toString())
                .setPrice(message.getPrice())
                .setOpen24h(message.getOpen24h())
                .setVolume24h(message.getVolume24h())
                .setLow24h(message.getLow24h())
                .setHigh24h(message.getHigh24h())
                .setVolume30d(message.getVolume30d())
                .setBestBid(message.getBestBid())
                .setBestBidSize(message.getBestBidSize())
                .setBestAsk(message.getBestAsk())
                .setBestAskSize(message.getBestAskSize())
                .setSide(message.getSide())
                .setTradeId(message.getTradeId())
                .setLastSize(message.getLastSize())
                .build();
    }

    /**
     * Encode an Avro SpecificRecord to bytes.
     */
    private <T extends org.apache.avro.specific.SpecificRecord> byte[] encodeAvro(T record) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            DatumWriter<T> writer = new SpecificDatumWriter<>(record.getSchema());
            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            writer.write(record, encoder);
            encoder.flush();
            return out.toByteArray();
        }
    }
}
