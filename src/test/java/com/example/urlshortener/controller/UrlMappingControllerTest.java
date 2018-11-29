package com.example.urlshortener.controller;

import com.example.urlshortener.domain.UrlMapping;
import com.example.urlshortener.repository.UrlMappingRepository;
import com.example.urlshortener.service.dto.RedirectInfoDTO;
import com.example.urlshortener.service.dto.UrlRegistrationResponseDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class UrlMappingControllerTest extends RestIntegrationTest {

    @Autowired
    UrlMappingRepository urlMappingRepository;

    @Test
    public void registerUrl_urlParameterMissing() {
        RedirectInfoDTO redirectInfoDTO = new RedirectInfoDTO();
        ResponseEntity<UrlRegistrationResponseDTO> response = withAuthTestRestTemplate().postForEntity("/register",redirectInfoDTO, UrlRegistrationResponseDTO.class);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    public void registerUrl_urlParameterIsEmptyString() {
        RedirectInfoDTO redirectInfoDTO = new RedirectInfoDTO();
        redirectInfoDTO.setUrl("");
        ResponseEntity<UrlRegistrationResponseDTO> response = withAuthTestRestTemplate().postForEntity("/register",redirectInfoDTO, UrlRegistrationResponseDTO.class);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    public void registerUrl_invalidRedirectType() {
        RedirectInfoDTO redirectInfoDTO = new RedirectInfoDTO();
        redirectInfoDTO.setUrl("https://github.com/LejlaNukic");
        redirectInfoDTO.setRedirectType(500);
        ResponseEntity<UrlRegistrationResponseDTO> response = withAuthTestRestTemplate().postForEntity("/register",redirectInfoDTO, UrlRegistrationResponseDTO.class);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    public void registerUrl_successfully() {
        RedirectInfoDTO redirectInfoDTO = new RedirectInfoDTO();
        redirectInfoDTO.setUrl("https://github.com/LejlaNukic");
        redirectInfoDTO.setRedirectType(301);
        ResponseEntity<UrlRegistrationResponseDTO> response = withAuthTestRestTemplate().postForEntity("/register",redirectInfoDTO, UrlRegistrationResponseDTO.class);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void getStatistics_UnauthorizedRequest() {
        ResponseEntity<Object> response = withAuthTestRestTemplate().getForEntity("/statistic/lejla",Object.class);
        assertTrue(response.getStatusCode().value() == 401);
    }

    @Test
    public void getStatistics_successfully() {
        ResponseEntity<Object> response = withAuthTestRestTemplate().getForEntity("/statistic/user",Object.class);
        Map<String, Integer> map = (Map<String, Integer>) response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(!map.isEmpty());
    }


    @Test
    public void redirect_invalidMapping() {
        ResponseEntity<Void> response = withAuthTestRestTemplate().getForEntity("/invalidMappingString",Void.class);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    public void redirect_validMapping() {
        ResponseEntity<Void> response = withAuthTestRestTemplate().getForEntity("/ErnyIY",Void.class);
        assertTrue(response.getStatusCode().value() == 301);
    }

}