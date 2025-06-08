package com.fnk.services;


import com.fnk.data.dto.rest.CoinbaseConnectionRequest;

public interface WebSocketService {

    /**
     * Establish a new websocket connection.
     *
     * @param coinbaseConnectionRequest: Optionally specify product ids to subscribe to. If no productIds are
     * are specify, default ones will be used.
     */
    void connect(CoinbaseConnectionRequest coinbaseConnectionRequest);

    /**
     * Close websocket connection
     */
    void close();

}
