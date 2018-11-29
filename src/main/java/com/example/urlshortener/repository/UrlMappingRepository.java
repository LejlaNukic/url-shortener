package com.example.urlshortener.repository;

import com.example.urlshortener.domain.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    UrlMapping findOneByMappingString(String mappingString);
    List<UrlMapping> findByAccount_AccountId(String accountId);
    UrlMapping findOneByUrlAndAccount_AccountId(String url, String accountId);

    @Modifying
    @Transactional
    @Query("update UrlMapping  u set u.numberOfVisits = u.numberOfVisits + 1 where u.mappingString = :mappingString")
    void incrementNumberOfVisists(@Param("mappingString") String mappingString);
}
