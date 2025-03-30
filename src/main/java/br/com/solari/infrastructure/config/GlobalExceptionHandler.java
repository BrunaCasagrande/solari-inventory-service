package br.com.solari.infrastructure.config;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import br.com.solari.application.usecase.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", Instant.now());
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("error", "Business Exception");
    body.put("message", ex.getMessage());

    return ResponseEntity.badRequest().body(body);
  }
}
