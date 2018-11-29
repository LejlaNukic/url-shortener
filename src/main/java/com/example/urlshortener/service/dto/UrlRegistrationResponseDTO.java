package com.example.urlshortener.service.dto;

import com.example.urlshortener.domain.UrlMapping;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UrlRegistrationResponseDTO {
    private static final String site = "http://localhost:8080/";
    private String mappingString;

    public UrlRegistrationResponseDTO(UrlMapping urlMapping) {
        this.mappingString = site + urlMapping.getMappingString();
    }
}
