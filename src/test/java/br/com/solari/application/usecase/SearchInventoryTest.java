package br.com.solari.application.usecase;

import br.com.solari.application.domain.Inventory;
import br.com.solari.application.gateway.InventoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchInventoryTest {

    private InventoryGateway inventoryGateway;
    private SearchInventory searchInventory;

    @BeforeEach
    void setUp() {
        inventoryGateway = mock(InventoryGateway.class);
        searchInventory = new SearchInventory(inventoryGateway);
    }

    @Test
    void shouldReturnInventoriesWhenSkuExists() {
        String sku = "12345";
        Inventory inventory1 = Inventory.createInventory(sku, 10);
        Inventory inventory2 = Inventory.createInventory(sku, 5);

        when(inventoryGateway.findAllBySku(sku)).thenReturn(List.of(inventory1, inventory2));

        List<Inventory> result = searchInventory.execute(sku);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(10, result.get(0).getQuantity());
        assertEquals(5, result.get(1).getQuantity());
    }

    @Test
    void shouldReturnEmptyListWhenSkuDoesNotExist() {
        String sku = "99999";

        when(inventoryGateway.findAllBySku(sku)).thenReturn(List.of());

        List<Inventory> result = searchInventory.execute(sku);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}