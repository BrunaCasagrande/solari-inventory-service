package br.com.solari.application.usecase;

import br.com.solari.application.domain.Inventory;
import br.com.solari.application.gateway.InventoryGateway;
import br.com.solari.application.usecase.exception.InventoryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteInventoryTest {

    private InventoryGateway inventoryGateway;
    private DeleteInventory deleteInventory;

    @BeforeEach
    void setUp() {
        inventoryGateway = mock(InventoryGateway.class);
        deleteInventory = new DeleteInventory(inventoryGateway);
    }

    @Test
    void shouldDeleteInventoryWhenExists() {
        Integer inventoryId = 1;
        Inventory inventory = Inventory.createInventory("12345", 10);

        when(inventoryGateway.findById(inventoryId)).thenReturn(Optional.of(inventory));

        deleteInventory.execute(inventoryId);

        verify(inventoryGateway).deleteById(inventoryId);
    }

    @Test
    void shouldThrowExceptionWhenInventoryNotFound() {
        Integer inventoryId = 1;

        when(inventoryGateway.findById(inventoryId)).thenReturn(Optional.empty());

        InventoryNotFoundException exception = assertThrows(
                InventoryNotFoundException.class,
                () -> deleteInventory.execute(inventoryId)
        );

        assertEquals("Inventory with id=[1] not found.", exception.getMessage());
        verify(inventoryGateway, never()).deleteById(any());
    }
}