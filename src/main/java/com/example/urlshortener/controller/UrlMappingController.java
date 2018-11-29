package com.example.urlshortener.controller;

import com.example.urlshortener.controller.dto.ErrorMessageDTO;
import com.example.urlshortener.service.UrlMappingService;
import com.example.urlshortener.service.dto.RedirectInfoDTO;
import com.example.urlshortener.service.dto.UrlRegistrationResponseDTO;
import com.example.urlshortener.service.exceptions.EmptyPropertyException;
import com.example.urlshortener.service.exceptions.InvalidMappingStringException;
import com.example.urlshortener.service.exceptions.InvalidRedirectTypeValueException;
import com.example.urlshortener.service.exceptions.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.Map;

@RestController
public class UrlMappingController {

    @Autowired
    private UrlMappingService urlMappingService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UrlRegistrationResponseDTO registerUrl(@RequestBody RedirectInfoDTO redirectInfoDTO, Principal principal) throws MissingRequiredPropertiesException, EmptyPropertyException, InvalidRedirectTypeValueException {
        if(redirectInfoDTO.getUrl() == null) throw new MissingRequiredPropertiesException();
        if(redirectInfoDTO.getUrl().equals("")) throw new EmptyPropertyException("url can't be empty string!");
        if(redirectInfoDTO.getRedirectType() != null && redirectInfoDTO.getRedirectType()!=301 && redirectInfoDTO.getRedirectType()!=302) throw new InvalidRedirectTypeValueException("Allowed redirect type values are 301 | 302!");
        return urlMappingService.registerUrl(redirectInfoDTO, principal.getName());
    }

    @GetMapping("/statistic/{AccountId}")
    public Map<String, Integer> getStatistics(@PathVariable("AccountId") String accountId, Principal principal) throws UnauthorizedAccessException {
        if(!accountId.equals(principal.getName())) throw new UnauthorizedAccessException("You don't have permission for this statistic!");
        return urlMappingService.getStatistics(accountId);
    }

    @GetMapping("/{mappingString}")
    public ResponseEntity<Void> redirect(@PathVariable("mappingString") String mappingString) throws InvalidMappingStringException {
        RedirectInfoDTO redirectInfo = null;
        redirectInfo = urlMappingService.getRedirectInfo(mappingString);
        urlMappingService.incrementNumberOfAccessions(mappingString);
        return createRedirectResponseEntity(redirectInfo);
    }

    private ResponseEntity<Void> createRedirectResponseEntity(RedirectInfoDTO redirectInfoDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectInfoDTO.getUrl()));
        HttpStatus status = HttpStatus.FOUND;
        if(redirectInfoDTO.getRedirectType() == 301) status = HttpStatus.MOVED_PERMANENTLY;
        return new ResponseEntity<>(headers,status);
    }

    @ExceptionHandler(MissingRequiredPropertiesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDTO handleMissingUrl(MissingRequiredPropertiesException e) {
        return new ErrorMessageDTO("url is required!");
    }

    @ExceptionHandler(EmptyPropertyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDTO handleEmptyPropertyException(EmptyPropertyException e) {
        return new ErrorMessageDTO(e.getMessage());
    }

    @ExceptionHandler(InvalidRedirectTypeValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDTO handleInvalidRedirectTypeValueException(InvalidRedirectTypeValueException e) {
        return new ErrorMessageDTO(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessageDTO handleUnauthorizedAccessException(UnauthorizedAccessException e) {
        return new ErrorMessageDTO(e.getMessage());
    }

    @ExceptionHandler(InvalidMappingStringException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDTO handleInvalidMappingStringException(InvalidMappingStringException e) {
        return new ErrorMessageDTO(e.getMessage());
    }
}
