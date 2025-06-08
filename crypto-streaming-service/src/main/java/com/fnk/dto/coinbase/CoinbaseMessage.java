package com.fnk.dto.coinbase;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TickerMessage.class, name = "ticker"),
        @JsonSubTypes.Type(value = HeartbeatMessage.class, name = "heartbeat"),
        @JsonSubTypes.Type(value = SubscriptionsMessage.class, name = "subscriptions")
})
public class CoinbaseMessage {
    @JsonProperty("type")
    private String type;

    public CoinbaseMessage() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CoinbaseMessage{" +
                "type='" + type +
                '}';
    }

    public String partitioningKey(){
        return this.type;
    }
}
