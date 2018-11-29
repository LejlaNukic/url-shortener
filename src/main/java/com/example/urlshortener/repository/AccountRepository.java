package com.example.urlshortener.repository;

import com.example.urlshortener.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findOneByAccountId(String AccountId);
}
