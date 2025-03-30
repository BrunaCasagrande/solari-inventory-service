package br.com.solari.application.domain.exception;

public record ErrorDetail(String field, String errorMessage, Object rejectedValue) {}
