package com.fnk.services.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fnk.dto.CoinbaseConnectionRequest;
import com.fnk.dto.coinbase.CoinbaseMessage;
import com.fnk.dto.coinbase.SubscribeMessage;
import com.fnk.enums.CoinbaseMessageType;
import com.fnk.enums.ErrorCode;
import com.fnk.exceptions.AvroSerializationException;
import com.fnk.exceptions.SystemException;
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

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

@Singleton
@ClientEndpoint
public class CoinbaseWebSocketService implements WebSocketService {

    private final Logger LOGGER = LoggerFactory.getLogger(CoinbaseWebSocketService.class);
    private final ObjectMapper objectMapper;
    private final String websocketAddress;
    private final MessageProducer messageProducer;
    private final AvroConverter avroConverter;

    private SubscribeMessage subscribeMessage;
    private Session userSession;

    public CoinbaseWebSocketService(ObjectMapper objectMapper, @Value("${coinbase.websocket.address}") String websocketAddress, MessageProducer messageProducer, AvroConverter avroConverter) {
        this.objectMapper = objectMapper;
        this.websocketAddress = websocketAddress;
        this.messageProducer = messageProducer;
        this.avroConverter = avroConverter;
    }


    @Override
    public void connect(CoinbaseConnectionRequest coinbaseConnectionRequest) {
        this.subscribeMessage = SubscribeMessage.from(coinbaseConnectionRequest);

        try{
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(websocketAddress));
            LOGGER.info("Connected to Coinbase WebSocket.");
        } catch (Exception e) {
            this.close();
            throw new SystemException("Failed to connect to WebSocket", e, ErrorCode.CONNECTION_FAILURE);
        }
    }

    @Override
    public void close() {
        try {
            if (Objects.nonNull(userSession)) {
                userSession.close();
            }
        } catch (Exception e) {
            throw new SystemException("Failed to close connection to WebSocket", e, ErrorCode.SERVER_FAILURE);
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
            throw new SystemException("Failed to subscribe to WebSocket", e, ErrorCode.CONNECTION_FAILURE);
        }
    }

    @OnMessage
    public void onMessage(String message) {
        try {
            if(isSubscriptionRes(message)){
                // This is the first message coinbase sends through the socket
                return;
            }
            CoinbaseMessage coinbaseMessage = objectMapper.readValue(message, CoinbaseMessage.class);
            LOGGER.info("Read the following message from websocket: {}", coinbaseMessage.toString());

            // Stream data as avro bytes to kafka
            byte[] avroBytes = avroConverter.serializeToAvro(coinbaseMessage);
            messageProducer.send(buildPartitioningKey(coinbaseMessage), avroBytes);
        } catch (Exception e) {
            LOGGER.error("Failed to read and serialize message from websocket: {}", message, e);
            throw new AvroSerializationException("Failed to read and serialize message from websocket", e);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        LOGGER.info("WebSocket closed: {}", reason.toString());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        this.close();
        throw new SystemException("Error occurred from websocket", throwable, ErrorCode.SERVER_FAILURE);

    }

    private String buildPartitioningKey(CoinbaseMessage coinbaseMessage) {
        if(CoinbaseMessageType.HEARTBEAT.messageType.equals(coinbaseMessage.getType())){
            return String.format("%s-%s", CoinbaseMessageType.HEARTBEAT, coinbaseMessage.getProductId());
        }
        return String.format("%s-%s", CoinbaseMessageType.TICKER, coinbaseMessage.getProductId());
    }

    private boolean isSubscriptionRes(String message) throws IOException {
        JsonNode rootNode = objectMapper.readTree(message);
        String type = rootNode.has("type") ? rootNode.get("type").asText() : "";

        if ("subscriptions".equals(type)) {
            LOGGER.info("Received subscription message from websocket: {}", message);
            return true;
        }
        return false;
    }

}
