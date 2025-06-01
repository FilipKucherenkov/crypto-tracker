package com.fnk.services.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnk.dto.ExchangeConnectionRequest;
import com.fnk.dto.coinbase.CoinbaseMessage;
import com.fnk.dto.coinbase.SubscribeMessage;
import com.fnk.enums.CoinbaseMessageType;
import com.fnk.enums.ErrorCode;
import com.fnk.exceptions.SocketClientException;
import com.fnk.services.MessageProducer;
import com.fnk.services.WebSocketService;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.CloseReason;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Objects;

@Singleton
@ClientEndpoint
public class CoinbaseWebSocketService implements WebSocketService {

    private final Logger LOGGER = LoggerFactory.getLogger(CoinbaseWebSocketService.class);
    private final ObjectMapper objectMapper;
    private final String websocketAddress;
    private final MessageProducer messageProducer;

    private SubscribeMessage subscribeMessage;
    private Session userSession;

    public CoinbaseWebSocketService(ObjectMapper objectMapper, @Value("${coinbase.websocket.address}") String websocketAddress, MessageProducer messageProducer) {
        this.objectMapper = objectMapper;
        this.websocketAddress = websocketAddress;
        this.messageProducer = messageProducer;
    }


    @Override
    public void connect(ExchangeConnectionRequest exchangeConnectionRequest) {
        this.subscribeMessage = SubscribeMessage.from(exchangeConnectionRequest);

        try{
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(websocketAddress));
            LOGGER.info("Connected to Coinbase WebSocket.");
        } catch (Exception e) {
            this.close();
            throw new SocketClientException("Failed to connect to WebSocket", e, ErrorCode.CONNECTION_FAILURE);
        }
    }

    @Override
    public void close() {
        try {
            if (Objects.nonNull(userSession)) {
                userSession.close();
            }
        } catch (Exception e) {
            throw new SocketClientException("Failed to close connection to WebSocket", e, ErrorCode.FAILED_TO_CLOSE_SOCKET_CONNECTION);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.userSession = session;
        LOGGER.info("WebSocket opened: {}", session.getId());

        try {
            session.getAsyncRemote().sendText(subscribeMessage.toString());
            LOGGER.info("Subscription message sent.");
        } catch (Exception e) {
            this.close();
            throw new SocketClientException("Failed to subscribe to WebSocket", e, ErrorCode.CONNECTION_FAILURE);
        }
    }

    @OnMessage
    public void onMessage(String message) {
        CoinbaseMessage coinbaseMessage = null;
        try {
            coinbaseMessage = objectMapper.readValue(message, CoinbaseMessage.class);
        } catch (JsonProcessingException e) {
            this.close();
            throw new SocketClientException("Closing connection - could not parse message from coinbase: {}", e, ErrorCode.CONNECTION_FAILURE);
        }
        messageProducer.send(buildPartitioningKey(coinbaseMessage), coinbaseMessage);
    }



    @OnClose
    public void onClose(Session session, CloseReason reason) {
        LOGGER.info("WebSocket closed: {}", reason.toString());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        this.close();
        throw new SocketClientException("Error occurred from websocket", throwable, ErrorCode.WEBSOCKET_FAILURE);

    }

    private String buildPartitioningKey(CoinbaseMessage coinbaseMessage) {
        if(CoinbaseMessageType.HEARTBEAT.messageType.equals(coinbaseMessage.getType())){
            return String.format("%s-%s", CoinbaseMessageType.HEARTBEAT, coinbaseMessage.getProductId());
        }
        return String.format("%s-%s", CoinbaseMessageType.TICKER, coinbaseMessage.getProductId());
    }

}
