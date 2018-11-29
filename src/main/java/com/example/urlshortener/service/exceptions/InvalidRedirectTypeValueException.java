package com.example.urlshortener.service.exceptions;

public class InvalidRedirectTypeValueException extends Exception {
    public InvalidRedirectTypeValueException(String message) {
        super(message);
    }
}
