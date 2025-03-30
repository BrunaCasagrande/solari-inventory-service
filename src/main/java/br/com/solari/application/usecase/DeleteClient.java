package br.com.solari.application.usecase;

import br.com.solari.application.usecase.exception.ClientNotFoundException;
import br.com.solari.application.gateway.ClientGateway;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Transactional
public class DeleteClient {

  private final ClientGateway clientGateway;

  public void execute(final String cpf) {
    clientGateway.findByCpf(cpf).orElseThrow(() -> new ClientNotFoundException(cpf));
    clientGateway.deleteByCpf(cpf);
  }
}
