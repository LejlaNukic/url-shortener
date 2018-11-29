package com.example.urlshortener.controller;

import com.example.urlshortener.repository.AccountRepository;
import com.example.urlshortener.service.dto.AccountRegistrationRequestDTO;
import com.example.urlshortener.service.dto.AccountRegistrationResponseDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class AccountControllerTest extends RestIntegrationTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void registerAccount_successfulRegistration() {
        AccountRegistrationRequestDTO accountRegistrationRequestDTO = new AccountRegistrationRequestDTO("user123");
        ResponseEntity<AccountRegistrationResponseDTO> response =  withAuthTestRestTemplate().postForEntity("/account", accountRegistrationRequestDTO, AccountRegistrationResponseDTO.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(true,response.getBody().getSuccess());
    }

    @Test
    public void registerAccount_AccountIdIsEmptyString() {
        AccountRegistrationRequestDTO accountRegistrationRequestDTO = new AccountRegistrationRequestDTO("");
        ResponseEntity<AccountRegistrationResponseDTO> response =  withAuthTestRestTemplate().postForEntity("/account", accountRegistrationRequestDTO, AccountRegistrationResponseDTO.class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void registerAccount_AccountIdIsNull() {
        AccountRegistrationRequestDTO accountRegistrationRequestDTO = new AccountRegistrationRequestDTO(null);
        ResponseEntity<AccountRegistrationResponseDTO> response =  withAuthTestRestTemplate().postForEntity("/account", accountRegistrationRequestDTO, AccountRegistrationResponseDTO.class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(false, response.getBody().getSuccess());
    }

    @Test
    public void registerAccount_AccountIdIsAlreadyTaken() {
        AccountRegistrationRequestDTO accountRegistrationRequestDTO = new AccountRegistrationRequestDTO("user");
        ResponseEntity<AccountRegistrationResponseDTO> response =  withAuthTestRestTemplate().postForEntity("/account", accountRegistrationRequestDTO, AccountRegistrationResponseDTO.class);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(false, response.getBody().getSuccess());
    }
}