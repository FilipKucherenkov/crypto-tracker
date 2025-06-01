package com.fnk.exceptions;

import com.fnk.enums.ErrorCode;

public class SocketClientException extends RuntimeException {
    private final ErrorCode errorCode;

    public SocketClientException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}