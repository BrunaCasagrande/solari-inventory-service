package br.com.solari.application.usecase;

import br.com.solari.application.domain.Inventory;
import br.com.solari.application.dto.UpdateInventoryDto;
import br.com.solari.application.gateway.InventoryGateway;
import br.com.solari.application.usecase.exception.InventoryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateInventoryTest {

    private InventoryGateway inventoryGateway;
    private UpdateInventory updateInventory;

    @BeforeEach
    void setUp() {
        inventoryGateway = mock(InventoryGateway.class);
        updateInventory = new UpdateInventory(inventoryGateway);
    }

    @Test
    void shouldUpdateInventoryQuantities() {
        String sku = "12345";
        UpdateInventoryDto request = new UpdateInventoryDto(5);
        Inventory inventory1 = Inventory.createInventory(sku, 10);
        Inventory inventory2 = Inventory.createInventory(sku, 15);

        when(inventoryGateway.findAllBySku(sku)).thenReturn(List.of(inventory1, inventory2));
        when(inventoryGateway.update(any(Inventory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<Inventory> result = updateInventory.execute(sku, request);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(15, result.get(0).getQuantity());
        assertEquals(20, result.get(1).getQuantity());

        verify(inventoryGateway, times(2)).update(any(Inventory.class));
    }

    @Test
    void shouldThrowExceptionWhenInventoryNotFound() {
        String sku = "99999";
        UpdateInventoryDto request = new UpdateInventoryDto(5);

        when(inventoryGateway.findAllBySku(sku)).thenReturn(List.of());

        InventoryNotFoundException exception = assertThrows(
                InventoryNotFoundException.class,
                () -> updateInventory.execute(sku, request)
        );

        assertEquals("Inventory with sku=[99999] not found.", exception.getMessage());
        verify(inventoryGateway, never()).update(any());
    }

    @Test
    void shouldThrowExceptionForInsufficientStock() {
        String sku = "12345";
        UpdateInventoryDto request = new UpdateInventoryDto(-20);
        Inventory inventory = Inventory.createInventory(sku, 10);

        when(inventoryGateway.findAllBySku(sku)).thenReturn(List.of(inventory));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> updateInventory.execute(sku, request)
        );

        assertEquals("Insufficient stock for SKU: 12345", exception.getMessage());
        verify(inventoryGateway, never()).update(any());
    }
}