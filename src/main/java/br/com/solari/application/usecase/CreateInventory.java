package br.com.solari.application.usecase;

import br.com.solari.application.domain.Inventory;
import br.com.solari.application.gateway.InventoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateInventory {

  private final InventoryGateway inventoryGateway;

  public Inventory execute(final Inventory request) {
    final var newInventory = Inventory.createInventory(
            request.getSku(),
            request.getQuantity()
    );

    final var existingInventories = inventoryGateway.findAllBySku(request.getSku());

    if (!existingInventories.isEmpty()) {
      final var totalQuantity = existingInventories.stream()
              .mapToInt(Inventory::getQuantity)
              .sum() + request.getQuantity();

      final var first = existingInventories.get(0);
      first.setQuantity(totalQuantity);
      return inventoryGateway.update(first);
    }

    return inventoryGateway.save(newInventory);
  }
}

