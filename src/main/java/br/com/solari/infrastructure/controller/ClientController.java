package br.com.solari.infrastructure.controller;

import br.com.solari.application.domain.Client;
import br.com.solari.application.dto.UpdateClientDto;
import br.com.solari.infrastructure.presenter.ClientPresenter;
import br.com.solari.infrastructure.presenter.response.ClientPresenterResponse;
import br.com.solari.application.usecase.CreateClient;
import br.com.solari.application.usecase.DeleteClient;
import br.com.solari.application.usecase.SearchClient;
import br.com.solari.application.usecase.UpdateClient;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/solari/v1/clients")
@RequiredArgsConstructor
public class ClientController {

  private final CreateClient createClient;
  private final DeleteClient deleteClient;
  private final SearchClient searchClient;
  private final UpdateClient updateClient;

  private final ClientPresenter clientPresenter;

  @PostMapping
  public ResponseEntity<ClientPresenterResponse> create(@Valid @RequestBody final Client request) {
    final var clientCreated = this.createClient.execute(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{cpf}")
            .buildAndExpand(clientCreated.getCpf())
            .toUri();

    return ResponseEntity.created(location).body(clientPresenter.parseToResponse(clientCreated));
  }

  @GetMapping("/{cpf}")
  public ResponseEntity<ClientPresenterResponse> findByCpf(@PathVariable final String cpf) {
    return this.searchClient
        .execute(cpf)
        .map(client -> ResponseEntity.ok(clientPresenter.parseToResponse(client)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{cpf}")
  public ResponseEntity<ClientPresenterResponse> update(
      @PathVariable final String cpf, @Valid @RequestBody final UpdateClientDto request) {

    final var updatedClient = this.updateClient.execute(cpf, request);

    return ResponseEntity.ok(clientPresenter.parseToResponse(updatedClient));
  }

  @DeleteMapping("/{cpf}")
  public ResponseEntity<Void> delete(@PathVariable final String cpf) {
    this.deleteClient.execute(cpf);
    return ResponseEntity.noContent().build();
  }
}
