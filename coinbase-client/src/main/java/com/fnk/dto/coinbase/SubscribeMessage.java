package com.fnk.dto.coinbase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnk.dto.ExchangeConnectionRequest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public record SubscribeMessage(
        String type,
        List<String> product_ids,
        List<Object> channels
) {
    private static final List<String> DEFAULT_PRODUCTS = List.of("ETH-USD", "ETH-EUR");

    public static SubscribeMessage from(ExchangeConnectionRequest request){
        if(Objects.isNull(request.productIds()) || request.productIds().isEmpty()){
            return buildMessage(DEFAULT_PRODUCTS);
        }
        return buildMessage(request.productIds());
    }

    private static SubscribeMessage buildMessage(List<String> productIds){

        Map<String, Object> tickerChannel = new LinkedHashMap<>();
        tickerChannel.put("name", "ticker");
        tickerChannel.put("product_ids", productIds);

        List<Object> channels = List.of(
                "level2",
                "heartbeat",
                tickerChannel
        );

        return new SubscribeMessage(
                "subscribe",
                productIds,
                channels
        );
    }


    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
