package com.example.urlshortener.service;

import com.example.urlshortener.domain.Account;
import com.example.urlshortener.repository.AccountRepository;
import com.example.urlshortener.service.dto.AccountRegistrationRequestDTO;
import com.example.urlshortener.service.dto.AccountRegistrationResponseDTO;
import com.example.urlshortener.service.exceptions.AccountIdTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RandomStringGeneratorService randomStringGeneratorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = getAccount(username);
        if(account == null)
            throw new UsernameNotFoundException(username);
        return account;
    }

    @Transactional
    public AccountRegistrationResponseDTO registerAccount(AccountRegistrationRequestDTO accountRegistrationRequestDTO) throws AccountIdTakenException {

        if(getAccount(accountRegistrationRequestDTO.getAccountId()) != null) {
            throw new AccountIdTakenException(accountRegistrationRequestDTO.getAccountId());
        }

        String password = randomStringGeneratorService.generatePassword();
        String passwordEncoded = passwordEncoder.encode(password);
        Account newAccount = new Account(accountRegistrationRequestDTO.getAccountId(), passwordEncoded);
        Account saved = accountRepository.save(newAccount);
        return new AccountRegistrationResponseDTO(true,"Account created", password);

    }

    private Account getAccount(String accountId) {
        return accountRepository.findOneByAccountId(accountId);
    }

}
