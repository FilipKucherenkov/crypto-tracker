package com.fnk.exceptions;

import com.fnk.dto.SocketClientErrorResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
public class SocketClientExceptionHandler implements ExceptionHandler<SocketClientException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, SocketClientException exception) {
        SocketClientErrorResponse error = new SocketClientErrorResponse(
                exception.getErrorCode(),
                exception.getMessage()
        );
        return HttpResponse.serverError(error);
    }
}