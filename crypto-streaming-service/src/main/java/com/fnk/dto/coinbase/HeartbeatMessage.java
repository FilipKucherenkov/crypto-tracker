package com.fnk.dto.coinbase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeartbeatMessage extends CoinbaseMessage{
    @JsonProperty("sequence")
    private String sequence;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("time")
    private OffsetDateTime time;
    @JsonProperty("last_trade_id")
    private String lastTradeId;

    public HeartbeatMessage(){}

    @Override
    public String partitioningKey() {
        return String.format("%s-%s", getType(), this.productId);
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

    public OffsetDateTime getTime() {
        return time;
    }

    public void setTime(OffsetDateTime time) {
        this.time = time;
    }

    public String getLastTradeId() {
        return lastTradeId;
    }

    public void setLastTradeId(String lastTradeId) {
        this.lastTradeId = lastTradeId;
    }
}
