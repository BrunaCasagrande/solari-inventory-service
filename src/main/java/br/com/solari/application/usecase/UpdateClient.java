package br.com.solari.application.usecase;

import br.com.solari.application.domain.Client;
import br.com.solari.application.dto.UpdateClientDto;
import br.com.solari.application.usecase.exception.ClientNotFoundException;
import br.com.solari.application.gateway.ClientGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateClient {

  private final ClientGateway clientGateway;

  public Client execute(final String cpf, final UpdateClientDto request) {
    final Client existingClient =
        clientGateway.findByCpf(cpf).orElseThrow(() -> new ClientNotFoundException(cpf));

    existingClient.setName(request.getName());
    existingClient.setPhoneNumber(request.getPhoneNumber());
    existingClient.setEmail(request.getEmail());
    existingClient.setPassword(request.getPassword());
    existingClient.setAddress(request.getAddress());

    return clientGateway.update(existingClient);
  }
}
