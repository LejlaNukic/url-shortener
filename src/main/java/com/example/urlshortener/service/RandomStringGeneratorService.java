package com.example.urlshortener.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class RandomStringGeneratorService {

    private static final String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int DEFAULT_PASSWORD_LENGTH = 8;
    private static final int DEFAULT_SHORTURL_LENGTH = 6;

    public String generateAlphanumericString(int stringLength) {
        String generatedString="";
        SecureRandom random = new SecureRandom();

        for(int i = 0; i < stringLength; i++) {
            int index = random.nextInt(alphanumericCharacters.length());
            generatedString += alphanumericCharacters.charAt(index);
        }
        return generatedString;
    }

    public String generatePassword() {
        return generateAlphanumericString(DEFAULT_PASSWORD_LENGTH);
    }

    public String generateMappingString() {
        return generateAlphanumericString(DEFAULT_SHORTURL_LENGTH);
    }

}
