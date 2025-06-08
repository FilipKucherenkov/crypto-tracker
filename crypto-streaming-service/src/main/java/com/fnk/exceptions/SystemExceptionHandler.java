package com.fnk.exceptions;

import com.fnk.data.dto.rest.SystemErrorResponse;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
public class SystemExceptionHandler implements ExceptionHandler<SystemException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, SystemException exception) {
        SystemErrorResponse error = new SystemErrorResponse(
                exception.getErrorCode(),
                exception.getMessage()
        );
        return HttpResponse.serverError(error);
    }
}