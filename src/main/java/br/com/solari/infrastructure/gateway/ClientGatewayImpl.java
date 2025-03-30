package br.com.solari.infrastructure.gateway;

import static java.lang.String.format;

import br.com.solari.application.domain.Address;
import br.com.solari.application.domain.Client;
import br.com.solari.application.gateway.exception.GatewayException;
import br.com.solari.application.gateway.ClientGateway;
import br.com.solari.infrastructure.persistence.entity.AddressEntity;
import br.com.solari.infrastructure.persistence.entity.ClientEntity;
import br.com.solari.infrastructure.persistence.repository.ClientRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientGatewayImpl implements ClientGateway {

  private final ClientRepository clientRepository;
  private static final String FIND_ERROR_MESSAGE = "Client with CPF=[%s] not found.";

  @Override
  public Client save(final Client client) {
    final var entity =
        ClientEntity.builder()
            .name(client.getName())
            .cpf(client.getCpf())
            .phoneNumber(client.getPhoneNumber())
            .email(client.getEmail())
            .password(client.getPassword())
            .address(toAddressEntity(client.getAddress()))
            .build();

    final var saved = clientRepository.save(entity);

    return this.toResponse(saved);
  }

  @Override
  public Optional<Client> findByCpf(final String cpf) {
    return clientRepository.findByCpf(cpf).map(this::toResponse);
  }

  @Override
  public Client update(final Client client) {
    try {
      final var entity =
          clientRepository
              .findByCpf(client.getCpf())
              .orElseThrow(() -> new GatewayException(format(FIND_ERROR_MESSAGE, client.getCpf())));

      entity.setName(client.getName());
      entity.setPhoneNumber(client.getPhoneNumber());
      entity.setEmail(client.getEmail());
      entity.setPassword(client.getPassword());
      entity.setAddress(toAddressEntity(client.getAddress()));

      final var updatedEntity = clientRepository.save(entity);

      return this.toResponse(updatedEntity);
    } catch (IllegalArgumentException e) {
      throw new GatewayException(format(FIND_ERROR_MESSAGE, client.getCpf()));
    }
  }

  @Override
  public void deleteByCpf(final String cpf) {
    clientRepository.deleteByCpf(cpf);
  }

  private Client toResponse(final ClientEntity entity) {
    return Client.builder()
        .id(entity.getId())
        .name(entity.getName())
        .cpf(entity.getCpf())
        .phoneNumber(entity.getPhoneNumber())
        .email(entity.getEmail())
        .password(entity.getPassword())
        .address(toAddressDomain(entity.getAddress()))
        .build();
  }

  private AddressEntity toAddressEntity(Address address) {
    if (address == null) return null;

    return AddressEntity.builder()
        .street(address.getStreet())
        .number(address.getNumber())
        .city(address.getCity())
        .state(address.getState())
        .zipCode(address.getZipCode())
        .build();
  }

  private Address toAddressDomain(AddressEntity entity) {
    if (entity == null) return null;

    return Address.builder()
        .id(entity.getId())
        .street(entity.getStreet())
        .number(entity.getNumber())
        .city(entity.getCity())
        .state(entity.getState())
        .zipCode(entity.getZipCode())
        .build();
  }
}
