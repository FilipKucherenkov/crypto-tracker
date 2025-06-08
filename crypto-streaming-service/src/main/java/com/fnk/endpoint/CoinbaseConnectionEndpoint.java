package com.fnk.endpoint;


import com.fnk.data.dto.rest.CoinbaseConnectionRequest;
import com.fnk.data.dto.rest.CoinbaseConnectionResponse;
import com.fnk.services.WebSocketService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/api/v1")
public class CoinbaseConnectionEndpoint {

    private final WebSocketService webSocketService;

    public CoinbaseConnectionEndpoint(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Post("/exchange-connection")
    public HttpResponse<CoinbaseConnectionResponse> connect(@Body CoinbaseConnectionRequest connectionRequest) {
        return switch (connectionRequest.connectionAction()) {
            case CONNECT -> {
                webSocketService.connect(connectionRequest);
                yield HttpResponse.ok(new CoinbaseConnectionResponse("connection opened successfully"));
            }
            case CLOSE_CONNECTION -> {
                webSocketService.close();
                yield HttpResponse.ok(new CoinbaseConnectionResponse("connection closed successfully"));
            }
        };

    }

}
