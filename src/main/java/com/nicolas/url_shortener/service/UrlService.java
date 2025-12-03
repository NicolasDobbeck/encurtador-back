package com.nicolas.url_shortener.service;

import com.nicolas.url_shortener.dto.ShortenUrlRequest;
import com.nicolas.url_shortener.dto.ShortenUrlResponse;
import com.nicolas.url_shortener.model.UrlEntity;
import com.nicolas.url_shortener.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlService {

    private final UrlRepository repository;

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    // Método principal: Encurtar
    public ShortenUrlResponse encurtar(ShortenUrlRequest request, HttpServletRequest servletRequest) {
        String hash;
        // Garante que o hash é único
        do {
            hash = gerarHashAleatorio();
        } while (repository.existsByHash(hash));

        UrlEntity entity = new UrlEntity();
        entity.setOriginalUrl(request.url());
        entity.setHash(hash);
        repository.save(entity);

        String baseUrl = servletRequest.getRequestURL().toString().replace(servletRequest.getRequestURI(), "");
        String urlEncurtada = baseUrl + "/" + hash;

        return new ShortenUrlResponse(entity.getOriginalUrl(), hash, urlEncurtada);
    }

    public UrlEntity buscarPorHash(String hash) {
        return repository.findByHash(hash)
                .orElseThrow(() -> new RuntimeException("URL não encontrada"));
    }

    private String gerarHashAleatorio() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder hash = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            hash.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return hash.toString();
    }
}