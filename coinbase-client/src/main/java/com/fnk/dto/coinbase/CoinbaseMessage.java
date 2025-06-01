package com.fnk.dto.coinbase;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class CoinbaseMessage {
    @JsonProperty("type")
    private String type;
    @JsonProperty("sequence")
    private String sequence;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("time")
    private OffsetDateTime time;

    public CoinbaseMessage() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "CoinbaseMessage{" +
                "type='" + type + '\'' +
                ", sequence='" + sequence + '\'' +
                ", productId='" + productId + '\'' +
                ", time=" + time +
                '}';
    }
}
