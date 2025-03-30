package br.com.solari.application.usecase.exception;

public class ClientNotFoundException extends BusinessException {
  private static final String MESSAGE = "Client with CPF=[%s] not found.";
  private static final String ERROR_CODE = "CLIENT_NOT_FOUND";

  public ClientNotFoundException(final String cpf) {
    super(String.format(MESSAGE, cpf), ERROR_CODE);
  }
}
