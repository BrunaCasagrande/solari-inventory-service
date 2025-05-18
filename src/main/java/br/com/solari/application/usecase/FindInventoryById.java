package br.com.solari.application.usecase;

import br.com.solari.application.domain.Inventory;
import br.com.solari.application.gateway.InventoryGateway;
import br.com.solari.application.usecase.exception.InventoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindInventoryById {

    private final InventoryGateway inventoryGateway;

    public Inventory execute(final Integer id) {
        return inventoryGateway.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException(id));
    }
}
