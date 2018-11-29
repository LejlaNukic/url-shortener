package com.example.urlshortener.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column
    private Integer redirectType;

    @Column(nullable = false)
    private String mappingString;

    @ManyToOne
    private Account account;

    @Column
    private Integer numberOfVisits;


    public UrlMapping(String url, String mappingString, Integer redirectType, Account account) {
        this.url = url;
        this.mappingString = mappingString;
        this.redirectType = redirectType;
        this.account = account;
        this.numberOfVisits = 0;
    }
}
