package com.nutech.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final Integer errorCode;

    public BusinessException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}