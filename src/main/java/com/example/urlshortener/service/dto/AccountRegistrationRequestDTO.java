package com.example.urlshortener.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountRegistrationRequestDTO {
    @JsonProperty("AccountId")
    private String accountId;

    public AccountRegistrationRequestDTO(String AccountId) {this.accountId = AccountId; }
}
