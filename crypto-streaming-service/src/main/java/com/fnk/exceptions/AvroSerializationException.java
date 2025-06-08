package com.fnk.exceptions;

/**
 * Custom runtime exception for serialization errors.
 */
public class AvroSerializationException extends SystemException {
    public AvroSerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}