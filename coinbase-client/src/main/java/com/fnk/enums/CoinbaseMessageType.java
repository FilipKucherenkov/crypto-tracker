package com.fnk.enums;

public enum CoinbaseMessageType {
    TICKER("ticker"),
    HEARTBEAT("heartbeat");

    public final String messageType;

    CoinbaseMessageType(String messageType){
        this.messageType = messageType;
    }
}
