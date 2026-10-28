package com.instantpay.app.shared.infra;

import java.time.Instant;

import org.springframework.http.HttpStatus;

public record RestErrorMessage(
    HttpStatus status,
    String message,
    Instant timestamp
) {
    public RestErrorMessage(HttpStatus status, String message) {
        this(status, message, Instant.now());
    }
}