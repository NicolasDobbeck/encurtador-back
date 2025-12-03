package com.nicolas.url_shortener.repository;

import com.nicolas.url_shortener.model.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    Optional<UrlEntity> findByHash(String hash);
    boolean existsByHash(String hash);
}