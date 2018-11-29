package com.example.urlshortener.service;

import com.example.urlshortener.domain.Account;
import com.example.urlshortener.domain.UrlMapping;
import com.example.urlshortener.repository.AccountRepository;
import com.example.urlshortener.repository.UrlMappingRepository;
import com.example.urlshortener.service.dto.RedirectInfoDTO;
import com.example.urlshortener.service.dto.UrlRegistrationResponseDTO;
import com.example.urlshortener.service.exceptions.InvalidMappingStringException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UrlMappingService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RandomStringGeneratorService randomStringGeneratorService;

    @Transactional
    public UrlRegistrationResponseDTO registerUrl(RedirectInfoDTO redirectInfoDTO, String accountId) {
            UrlMapping urlMapping = urlMappingRepository.findOneByUrlAndAccount_AccountId(redirectInfoDTO.getUrl(), accountId);
            if(urlMapping != null) {
                return new UrlRegistrationResponseDTO(urlMapping);
            }
            else {
                urlMapping = createUrlMapping(redirectInfoDTO, accountId);
                UrlMapping savedUrlMapping = urlMappingRepository.save(urlMapping);
                return new UrlRegistrationResponseDTO(savedUrlMapping);
            }
    }


    private UrlMapping createUrlMapping(RedirectInfoDTO redirectInfoDTO, String accountId) {
        String url = redirectInfoDTO.getUrl();
        int redirectType = 302;
        String mappingString = randomStringGeneratorService.generateMappingString();
        Account account = accountRepository.findOneByAccountId(accountId);
        if(redirectInfoDTO.getRedirectType()!= null) redirectType = redirectInfoDTO.getRedirectType();

        UrlMapping urlMapping = new UrlMapping(url, mappingString, redirectType, account);
        return urlMapping;
    }


    @Transactional
    public Map<String, Integer> getStatistics(String acountId) {
        return convertListToHashMap(urlMappingRepository.findByAccount_AccountId(acountId));
    }

    @Transactional
    public RedirectInfoDTO getRedirectInfo(String mappingString) throws InvalidMappingStringException {
        UrlMapping urlMapping = urlMappingRepository.findOneByMappingString(mappingString);
        if(urlMapping == null) throw new InvalidMappingStringException("Invalid short url!");
        RedirectInfoDTO redirectInfoDTO = new RedirectInfoDTO(urlMapping);
        return redirectInfoDTO;
    }

    @Transactional
    public void incrementNumberOfAccessions(String mappingString) {
        urlMappingRepository.incrementNumberOfVisists(mappingString);
    }

    private Map<String, Integer> convertListToHashMap(List<UrlMapping> urlMappings) {
        Map<String, Integer> hashMapStatistics = urlMappings.stream().collect(Collectors.toMap(UrlMapping::getUrl,UrlMapping::getNumberOfVisits));
        return hashMapStatistics;
    }
}
