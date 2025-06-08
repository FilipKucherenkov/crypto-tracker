package com.fnk.data.dto.websocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerMessage extends CoinbaseMessage {
    @JsonProperty("sequence")
    private String sequence;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("time")
    private OffsetDateTime time;

    // The current price of the product
    @JsonProperty("price")
    private double price;

    // Price at the start of the last 24 hours
    @JsonProperty("open_24h")
    private double open24h;

    // Trading volume over the last 24 hours
    @JsonProperty("volume_24h")
    private double volume24h;

    // Lowest price in the last 24 hours
    @JsonProperty("low_24h")
    private double low24h;

    // Highest price in the last 24 hours
    @JsonProperty("high_24h")
    private double high24h;

    // Trading volume over the last 30 days
    @JsonProperty("volume_30d")
    private double volume30d;

    // Best current bid price
    @JsonProperty("best_bid")
    private double bestBid;

    // Size (amount) at the best bid price
    @JsonProperty("best_bid_size")
    private double bestBidSize;

    // Best current ask price
    @JsonProperty("best_ask")
    private double bestAsk;

    // Size (amount) at the best ask price
    @JsonProperty("best_ask_size")
    private double bestAskSize;

    // Buy or sell indicator
    @JsonProperty("side")
    private String side;

    // Unique trade ID
    @JsonProperty("trade_id")
    private long tradeId;

    // The amount of the last trade
    @JsonProperty("last_size")
    private double lastSize;

    public TickerMessage() {}

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOpen24h() {
        return open24h;
    }

    public void setOpen24h(double open24h) {
        this.open24h = open24h;
    }

    public double getVolume24h() {
        return volume24h;
    }

    public void setVolume24h(double volume24h) {
        this.volume24h = volume24h;
    }

    public double getLow24h() {
        return low24h;
    }

    public void setLow24h(double low24h) {
        this.low24h = low24h;
    }

    public double getHigh24h() {
        return high24h;
    }

    public void setHigh24h(double high24h) {
        this.high24h = high24h;
    }

    public double getVolume30d() {
        return volume30d;
    }

    public void setVolume30d(double volume30d) {
        this.volume30d = volume30d;
    }

    public double getBestBid() {
        return bestBid;
    }

    public void setBestBid(double bestBid) {
        this.bestBid = bestBid;
    }

    public double getBestBidSize() {
        return bestBidSize;
    }

    public void setBestBidSize(double bestBidSize) {
        this.bestBidSize = bestBidSize;
    }

    public double getBestAsk() {
        return bestAsk;
    }

    public void setBestAsk(double bestAsk) {
        this.bestAsk = bestAsk;
    }

    public double getBestAskSize() {
        return bestAskSize;
    }

    public void setBestAskSize(double bestAskSize) {
        this.bestAskSize = bestAskSize;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public long getTradeId() {
        return tradeId;
    }

    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }

    public double getLastSize() {
        return lastSize;
    }

    public void setLastSize(double lastSize) {
        this.lastSize = lastSize;
    }

    @Override
    public String toString() {
        return "TickerMessage{" +
                "price=" + price +
                ", open24h=" + open24h +
                ", volume24h=" + volume24h +
                ", low24h=" + low24h +
                ", high24h=" + high24h +
                ", volume30d=" + volume30d +
                ", bestBid=" + bestBid +
                ", bestBidSize=" + bestBidSize +
                ", bestAsk=" + bestAsk +
                ", bestAskSize=" + bestAskSize +
                ", side='" + side + '\'' +
                ", tradeId=" + tradeId +
                ", lastSize=" + lastSize +
                '}';
    }


}
