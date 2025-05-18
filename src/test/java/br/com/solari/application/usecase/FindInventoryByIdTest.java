package br.com.solari.application.usecase;

import br.com.solari.application.domain.Inventory;
import br.com.solari.application.gateway.InventoryGateway;
import br.com.solari.application.usecase.exception.InventoryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindInventoryByIdTest {

    private InventoryGateway inventoryGateway;
    private FindInventoryById findInventoryById;

    @BeforeEach
    void setUp() {
        inventoryGateway = mock(InventoryGateway.class);
        findInventoryById = new FindInventoryById(inventoryGateway);
    }

    @Test
    void shouldReturnInventoryWhenExists() {
        Integer inventoryId = 1;
        Inventory inventory = Inventory.createInventory("12345", 10);
        inventory.setId(inventoryId);

        when(inventoryGateway.findById(inventoryId)).thenReturn(Optional.of(inventory));

        Inventory result = findInventoryById.execute(inventoryId);

        assertNotNull(result);
        assertEquals(inventoryId, result.getId());
        assertEquals("12345", result.getSku());
        assertEquals(10, result.getQuantity());
    }

    @Test
    void shouldThrowExceptionWhenInventoryNotFound() {
        Integer inventoryId = 1;

        when(inventoryGateway.findById(inventoryId)).thenReturn(Optional.empty());

        InventoryNotFoundException exception = assertThrows(
                InventoryNotFoundException.class,
                () -> findInventoryById.execute(inventoryId)
        );

        assertEquals("Inventory with id=[1] not found.", exception.getMessage());
    }
}