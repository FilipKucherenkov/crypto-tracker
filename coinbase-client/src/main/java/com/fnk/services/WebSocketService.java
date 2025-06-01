package com.fnk.services;


import com.fnk.dto.ExchangeConnectionRequest;

public interface WebSocketService {

    /**
     * Establish a new websocket connection.
     *
     * @param exchangeConnectionRequest: Optionally specify product ids to subscribe to. If no productIds are
     * are specify, default ones will be used.
     */
    void connect(ExchangeConnectionRequest exchangeConnectionRequest);

    /**
     * Close websocket connection
     */
    void close();

}
