package com.example.urlshortener.service;

import com.example.urlshortener.domain.UrlMapping;
import com.example.urlshortener.repository.UrlMappingRepository;
import com.example.urlshortener.service.dto.RedirectInfoDTO;
import com.example.urlshortener.service.exceptions.InvalidMappingStringException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlMappingServiceTest {

    @Autowired
    UrlMappingService urlMappingService;

    @Autowired
    UrlMappingRepository urlMappingRepository;

   @Test
    public void registerUrl_SuccessfulRegistration() {
       RedirectInfoDTO redirectInfoDTO = new RedirectInfoDTO();
       redirectInfoDTO.setUrl("https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/");
       redirectInfoDTO.setRedirectType(301);
       String mappingString = urlMappingService.registerUrl(redirectInfoDTO, "user").getMappingString();
       assertNotEquals(mappingString,null);
    }

    @Test
    public void registerUrl_SuccessfulRegistrationWithoutRedirectType() {
        RedirectInfoDTO redirectInfoDTO = new RedirectInfoDTO();
        redirectInfoDTO.setUrl("https://www.baeldung.com/spring-boot-start");
        String mappingString = urlMappingService.registerUrl(redirectInfoDTO, "user").getMappingString();
        assertNotEquals(mappingString,null);
    }

    @Test
    public void registerUrl_UserAlreadyHasMapping() {
        RedirectInfoDTO redirectInfoDTO = new RedirectInfoDTO();
        redirectInfoDTO.setUrl("https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html");
        redirectInfoDTO.setRedirectType(301);
        String mappingString = urlMappingService.registerUrl(redirectInfoDTO, "user").getMappingString();
        assertEquals("http://localhost:8080/ErnyIY",mappingString);
    }

    @Test
    public void getStatistics_NoMappings() {
        Map map = urlMappingService.getStatistics("lejla");
        assertTrue(map.isEmpty());
    }

    @Test
    public void getStatistics() {
        Map map = urlMappingService.getStatistics("user");
        assertTrue(!map.isEmpty());
    }

    @Test(expected = InvalidMappingStringException.class)
    public void getRedirectInfo_InvalidString() throws InvalidMappingStringException {
        String url = urlMappingService.getRedirectInfo("xxUnkn").getUrl();
    }

    @Test
    public void getRedirectInfo() throws InvalidMappingStringException {
       String url = urlMappingService.getRedirectInfo("ErnyIY").getUrl();
       assertEquals(url, "https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html");
    }

    @Test
    public void incrementNumberOfAccessions() {
       String mappingString = "ErnyIY";
       int numberOfVisitsBefore = urlMappingRepository.findOneByMappingString(mappingString).getNumberOfVisits();
       urlMappingService.incrementNumberOfAccessions(mappingString);
       int numberOfVisitsLater = urlMappingRepository.findOneByMappingString(mappingString).getNumberOfVisits();
       assertEquals(numberOfVisitsBefore+1,numberOfVisitsLater);
    }

}