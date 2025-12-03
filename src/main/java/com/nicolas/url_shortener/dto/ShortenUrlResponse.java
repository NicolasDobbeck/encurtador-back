package com.nicolas.url_shortener.dto;

public record ShortenUrlResponse(String urlOriginal, String hash, String urlEncurtada) {
}