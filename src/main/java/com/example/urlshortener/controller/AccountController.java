package com.example.urlshortener.controller;
import com.example.urlshortener.service.AccountService;
import com.example.urlshortener.service.dto.AccountRegistrationRequestDTO;
import com.example.urlshortener.service.dto.AccountRegistrationResponseDTO;
import com.example.urlshortener.service.exceptions.AccountIdTakenException;
import com.example.urlshortener.service.exceptions.EmptyPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(path = "/account")
    @ResponseStatus(CREATED)
    public AccountRegistrationResponseDTO registerAccount(@RequestBody AccountRegistrationRequestDTO accountRegistrationRequestDTO) throws MissingRequiredPropertiesException, AccountIdTakenException, EmptyPropertyException {
        if(accountRegistrationRequestDTO.getAccountId() == null) throw new MissingRequiredPropertiesException();
        if(accountRegistrationRequestDTO.getAccountId().equals("")) throw new EmptyPropertyException("AccountId can't be empty string!");
        try {
            return accountService.registerAccount(accountRegistrationRequestDTO);
        } catch (AccountIdTakenException e) {
            throw e;
        }
    }

    @ExceptionHandler(MissingRequiredPropertiesException.class)
    @ResponseStatus(BAD_REQUEST)
    public AccountRegistrationResponseDTO handleMissingAccountId() {
        return new AccountRegistrationResponseDTO(false,"AccountId is required!",null);
    }

    @ExceptionHandler(AccountIdTakenException.class)
    @ResponseStatus(BAD_REQUEST)
    public AccountRegistrationResponseDTO handleAccountIdTaken(AccountIdTakenException e) {
        return new AccountRegistrationResponseDTO(false, e.getMessage(),null);
    }

    @ExceptionHandler(EmptyPropertyException.class)
    @ResponseStatus(BAD_REQUEST)
    public AccountRegistrationResponseDTO handleEmptyAccountId(EmptyPropertyException e) {
        return new AccountRegistrationResponseDTO(false,e.getMessage(), null);
    }

}
