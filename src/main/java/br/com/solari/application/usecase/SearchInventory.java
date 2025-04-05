package br.com.solari.application.usecase;

import br.com.solari.application.domain.Inventory;
import br.com.solari.application.gateway.InventoryGateway;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchInventory {

  private final InventoryGateway inventoryGateway;

  public List<Inventory> execute(final String sku) {
    return inventoryGateway.findAllBySku(sku);
  }
}
