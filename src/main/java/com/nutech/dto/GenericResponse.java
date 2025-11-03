package com.nutech.dto;

import lombok.Data;

@Data
public class GenericResponse<T> {
    private int status;
    private String message;
    private T data;
}