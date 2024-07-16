package com.werp.bankApp.service;

public class CustomServiceException extends RuntimeException {
    public CustomServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
