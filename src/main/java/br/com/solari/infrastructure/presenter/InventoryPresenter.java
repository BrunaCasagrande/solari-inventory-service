package br.com.solari.infrastructure.presenter;

import br.com.solari.application.domain.Inventory;
import br.com.solari.infrastructure.presenter.response.InventoryPresenterResponse;
import org.springframework.stereotype.Component;

@Component
public class InventoryPresenter {

  public InventoryPresenterResponse parseToResponse(final Inventory inventory) {
    return InventoryPresenterResponse.builder()
            .id(inventory.getId())
            .sku(inventory.getSku())
            .quantity(inventory.getQuantity())
            .build();
  }
}
