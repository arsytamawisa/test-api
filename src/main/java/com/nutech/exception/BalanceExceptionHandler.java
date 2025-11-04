package com.nutech.exception;

import com.nutech.dto.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BalanceExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GenericResponse<Object>> handleBalanceJsonParseException(HttpMessageNotReadableException ex) {
        GenericResponse<Object> response = GenericResponse.builder()
                .status(102)
                .message("Parameter amount hanya boleh angka dan tidak boleh lebih kecil dari 0")
                .data(null)
                .build();

        return ResponseEntity.badRequest().body(response);
    }
}