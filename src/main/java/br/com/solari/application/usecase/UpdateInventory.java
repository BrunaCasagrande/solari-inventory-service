package br.com.solari.application.usecase;

import br.com.solari.application.domain.Inventory;
import br.com.solari.application.dto.UpdateInventoryDto;
import br.com.solari.application.gateway.InventoryGateway;
import br.com.solari.application.usecase.exception.InventoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateInventory {

  private final InventoryGateway inventoryGateway;

  public List<Inventory> execute(final String sku, final UpdateInventoryDto request) {
    final List<Inventory> inventories = inventoryGateway.findAllBySku(sku);

    if (inventories.isEmpty()) {
      throw new InventoryNotFoundException(sku);
    }

    inventories.forEach(inventory ->
            inventory.setQuantity(inventory.getQuantity() + request.getQuantity()));

    return inventories.stream()
            .map(inventoryGateway::update)
            .toList();
  }
}
