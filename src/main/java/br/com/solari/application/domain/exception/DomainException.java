package br.com.solari.application.domain.exception;

import java.util.List;
import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

  private final String errorCode;
  private final String message;
  private final List<ErrorDetail> errors;

  public DomainException(final String message, final String errorCode, List<ErrorDetail> errors) {
    super(message);

    this.message = message;
    this.errorCode = errorCode;
    this.errors = errors;
  }
}
