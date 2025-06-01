package com.fnk.services.impl;

import com.fnk.dto.coinbase.CoinbaseMessage;
import com.fnk.dto.coinbase.CoinbaseMessageAvro;
import com.fnk.dto.coinbase.TickerMessage;
import com.fnk.dto.coinbase.TickerMessageAvro;
import jakarta.inject.Singleton;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Singleton
public class AvroConverter {

    /**
     * Serialize a coinbase message dto to avro format
     */
    public byte[] serializeToAvro(CoinbaseMessage coinbaseMessage) throws IOException {
        if (coinbaseMessage instanceof TickerMessage tickerMessage) {
            TickerMessageAvro avroMessage = mapToAvro(tickerMessage);
            return encodeAvro(avroMessage);
        } else {
            CoinbaseMessageAvro avroMessage = mapToAvro(coinbaseMessage);
            return encodeAvro(avroMessage);
        }
    }

    /**
     * Map CoinbaseMessage to CoinbaseMessageAvro.
     */
    private CoinbaseMessageAvro mapToAvro(CoinbaseMessage message) {
        return CoinbaseMessageAvro.newBuilder()
                .setType(message.getType())
                .setSequence(message.getSequence())
                .setProductId(message.getProductId())
                .setTime(message.getTime().toString())
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
