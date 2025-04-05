package br.com.solari.infrastructure.gateway;

import br.com.solari.application.domain.Inventory;
import br.com.solari.application.gateway.InventoryGateway;
import br.com.solari.application.gateway.exception.GatewayException;
import br.com.solari.infrastructure.persistence.entity.InventoryEntity;
import br.com.solari.infrastructure.persistence.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InventoryGatewayImpl implements InventoryGateway {

  private final InventoryRepository inventoryRepository;
  private static final String FIND_ERROR_MESSAGE = "Inventory with id=[%s] not found.";

  @Override
  public Inventory save(final Inventory inventory) {
    final var entity = InventoryEntity.builder()
            .sku(inventory.getSku())
            .quantity(inventory.getQuantity())
            .build();

    final var saved = inventoryRepository.save(entity);

    return this.toResponse(saved);
  }

  @Override
  public Inventory update(final Inventory inventory) {
    final var entity = inventoryRepository.findById(inventory.getId())
            .orElseThrow(() -> new GatewayException(String.format(FIND_ERROR_MESSAGE, inventory.getId())));

    entity.setQuantity(inventory.getQuantity());

    final var updated = inventoryRepository.save(entity);

    return this.toResponse(updated);
  }

  @Override
  public void deleteById(final Integer id) {
    inventoryRepository.deleteById(id);
  }

  @Override
  public Optional<Inventory> findById(final Integer id) {
    return inventoryRepository.findById(id).map(this::toResponse);
  }

  @Override
  public List<Inventory> findAllBySku(final String sku) {
    return inventoryRepository.findAllBySku(sku).stream()
            .map(this::toResponse)
            .toList();
  }

  private Inventory toResponse(final InventoryEntity entity) {
    return Inventory.builder()
            .id(entity.getId())
            .sku(entity.getSku())
            .quantity(entity.getQuantity())
            .build();
  }
}
