package com.fnk.enums;

import com.fnk.dto.coinbase.CoinbaseMessage;

public enum CoinbaseMessageType {
    TICKER("ticker"),
    HEARTBEAT("heartbeat");

    public final String messageType;

    CoinbaseMessageType(String messageType){
        this.messageType = messageType;
    }
}
