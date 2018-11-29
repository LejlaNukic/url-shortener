package com.example.urlshortener.service.exceptions;

public class AccountIdTakenException extends Exception {
    public AccountIdTakenException(String accountId) {
        super(String.format("AccountId '%s' is already taken!", accountId));
    }
}
