package com.fnk.exceptions;

import com.fnk.data.enums.ErrorCode;


/**
 * Custom runtime exception any socket related errors
 */
public class SystemException extends RuntimeException {
    private final ErrorCode errorCode;

    public SystemException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = ErrorCode.SERVER_FAILURE;
    }

    public SystemException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}