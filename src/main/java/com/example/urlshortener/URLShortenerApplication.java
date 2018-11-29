package com.example.urlshortener;

import com.example.urlshortener.domain.Account;
import com.example.urlshortener.domain.UrlMapping;
import com.example.urlshortener.repository.AccountRepository;
import com.example.urlshortener.repository.UrlMappingRepository;
import com.example.urlshortener.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class URLShortenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(URLShortenerApplication.class, args);
    }

    @Component
    private class Runner implements ApplicationRunner {

        @Autowired
        private AccountService accountService;

        @Autowired
        private AccountRepository accountRepository;

        @Autowired
        private UrlMappingRepository urlMappingRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            accountRepository.save(new Account("user", passwordEncoder.encode("password")));
            accountRepository.save(new Account("lejla",passwordEncoder.encode("1dvatri!")));

            urlMappingRepository.save(new UrlMapping("https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html","ErnyIY",301,accountRepository.findOneByAccountId("user")));


        }
    }
}
