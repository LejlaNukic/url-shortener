package com.example.urlshortener.service.dto;

import com.example.urlshortener.domain.UrlMapping;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RedirectInfoDTO {
    private String url;
    private Integer redirectType;

    public RedirectInfoDTO(UrlMapping urlMapping) {
        this.url = urlMapping.getUrl();
        this.redirectType = urlMapping.getRedirectType();
    }
}
