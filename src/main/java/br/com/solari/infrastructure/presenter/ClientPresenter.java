package br.com.solari.infrastructure.presenter;

import br.com.solari.application.domain.Client;
import br.com.solari.infrastructure.presenter.response.ClientPresenterResponse;
import org.springframework.stereotype.Component;

@Component
public class ClientPresenter {

  public ClientPresenterResponse parseToResponse(final Client client) {
    return ClientPresenterResponse.builder()
        .id(client.getId())
        .name(client.getName())
        .cpf(client.getCpf())
        .phoneNumber(client.getPhoneNumber())
        .email(client.getEmail())
        .address(client.getAddress())
        .build();
  }
}
