package com.example.urlshortener.service;

import com.example.urlshortener.service.dto.AccountRegistrationRequestDTO;
import com.example.urlshortener.service.exceptions.AccountIdTakenException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Test
    public void loadUserByUsername() {
        UserDetails userDetails = accountService.loadUserByUsername("user");
        assertNotEquals(userDetails,null);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_NoUser() {
        UserDetails userDetails = accountService.loadUserByUsername("unregisteredUser");
    }

    @Test
    public void registerAccount_SuccessfulRegistration() {
        String password = null;
        try {
            password = accountService.registerAccount(new AccountRegistrationRequestDTO("username")).getPassword();
        }
        catch (AccountIdTakenException e) {
            e.printStackTrace();
        }
        assertNotEquals(password,null);
    }

    @Test(expected = AccountIdTakenException.class)
    public void registerAccount_AccountIdTaken() throws AccountIdTakenException {
        String password = accountService.registerAccount(new AccountRegistrationRequestDTO("user")).getPassword();
    }
}