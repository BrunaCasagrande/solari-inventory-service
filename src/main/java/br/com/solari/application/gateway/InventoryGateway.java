package br.com.solari.application.gateway;

import br.com.solari.application.domain.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryGateway {

  Inventory save(final Inventory inventory);

  Inventory update(final Inventory inventory);

  void deleteById(final Integer id);

  Optional<Inventory> findById(final Integer id);

  List<Inventory> findAllBySku(final String sku);
}
