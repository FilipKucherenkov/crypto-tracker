package com.fnk.services;


import com.fnk.dto.ExchangeConnectionRequest;

public interface WebSocketService {

    void connect(ExchangeConnectionRequest exchangeConnectionRequest);

    void close();

}
