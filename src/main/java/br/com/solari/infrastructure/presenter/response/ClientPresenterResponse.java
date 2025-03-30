package br.com.solari.infrastructure.presenter.response;

import br.com.solari.application.domain.Address;
import lombok.Builder;

@Builder
public record ClientPresenterResponse(
    int id, String name, String cpf, String phoneNumber, String email, Address address) {}
