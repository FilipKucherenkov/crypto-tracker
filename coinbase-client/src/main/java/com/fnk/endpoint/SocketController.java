package com.fnk.endpoint;


import com.fnk.dto.ExchangeConnectionRequest;
import com.fnk.dto.ExchangeConnectionResponse;
import com.fnk.services.WebSocketService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/api/v1")
public class SocketController {

    private final WebSocketService webSocketService;

    public SocketController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Post("/exchange-connection")
    public HttpResponse<ExchangeConnectionResponse> connect(@Body ExchangeConnectionRequest connectionRequest) {

        return switch (connectionRequest.connectionAction()) {
            case CONNECT -> {
                webSocketService.connect(connectionRequest);
                yield HttpResponse.ok(new ExchangeConnectionResponse("connection opened successfully"));
            }
            case CLOSE_CONNECTION -> {
                webSocketService.close();
                yield HttpResponse.ok(new ExchangeConnectionResponse("connection closed successfully"));
            }
        };

    }

}
