package com.nicolas.url_shortener.controller;

import com.nicolas.url_shortener.dto.ShortenUrlRequest;
import com.nicolas.url_shortener.dto.ShortenUrlResponse;
import com.nicolas.url_shortener.model.UrlEntity;
import com.nicolas.url_shortener.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlController {

    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> encurtarUrl(@RequestBody ShortenUrlRequest request, HttpServletRequest servletRequest) {
        ShortenUrlResponse response = service.encurtar(request, servletRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{hash}")
    public ResponseEntity<Void> redirecionar(@PathVariable String hash) {
        UrlEntity url = service.buscarPorHash(hash);

        return ResponseEntity.status(HttpStatus.FOUND) // 302
                .location(URI.create(url.getOriginalUrl()))
                .build();
    }
}