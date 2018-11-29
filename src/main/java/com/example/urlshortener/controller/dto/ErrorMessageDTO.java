package com.example.urlshortener.controller.dto;

import lombok.Getter;

@Getter
public class ErrorMessageDTO {
    private String message;

    public ErrorMessageDTO(String message) {
        this.message = message;
    }
}
