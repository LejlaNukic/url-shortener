package com.example.urlshortener.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountRegistrationResponseDTO {
    private Boolean success;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    public AccountRegistrationResponseDTO(Boolean success, String description, String password) {
        this.success = success;
        this.description = description;
        this.password = password;
    }
}
