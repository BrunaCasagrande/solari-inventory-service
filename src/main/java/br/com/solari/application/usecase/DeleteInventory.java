package br.com.solari.application.usecase;

import br.com.solari.application.usecase.exception.InventoryNotFoundException;
import br.com.solari.application.gateway.InventoryGateway;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Transactional
public class DeleteInventory {

  private final InventoryGateway inventoryGateway;

  public void execute(final Integer id) {
    inventoryGateway.findById(id)
            .orElseThrow(() -> new InventoryNotFoundException(id));
    inventoryGateway.deleteById(id);
  }
}
