package br.com.solari.application.usecase;

import br.com.solari.application.domain.Inventory;
import br.com.solari.application.domain.exception.DomainException;
import br.com.solari.application.gateway.InventoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateInventoryTest {

    private InventoryGateway inventoryGateway;
    private CreateInventory createInventory;

    @BeforeEach
    void setUp() {
        inventoryGateway = mock(InventoryGateway.class);
        createInventory = new CreateInventory(inventoryGateway);
    }

    @Test
    void shouldCreateNewInventoryWhenNoExistingInventory() {
        Inventory request = Inventory.createInventory("12345", 10);

        when(inventoryGateway.findAllBySku("12345")).thenReturn(List.of());
        when(inventoryGateway.save(any(Inventory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Inventory result = createInventory.execute(request);

        assertNotNull(result);
        assertEquals("12345", result.getSku());
        assertEquals(10, result.getQuantity());

        ArgumentCaptor<Inventory> captor = ArgumentCaptor.forClass(Inventory.class);
        verify(inventoryGateway).save(captor.capture());
        Inventory savedInventory = captor.getValue();

        assertEquals("12345", savedInventory.getSku());
        assertEquals(10, savedInventory.getQuantity());
    }

    @Test
    void shouldUpdateExistingInventoryQuantity() {
        Inventory existingInventory = Inventory.createInventory("12345", 5);
        Inventory request = Inventory.createInventory("12345", 10);

        when(inventoryGateway.findAllBySku("12345")).thenReturn(List.of(existingInventory));
        when(inventoryGateway.update(any(Inventory.class))).thenReturn(existingInventory);

        Inventory result = createInventory.execute(request);

        assertNotNull(result);
        assertEquals("12345", result.getSku());
        assertEquals(15, result.getQuantity());
        verify(inventoryGateway).update(existingInventory);
    }

    @Test
    void shouldThrowExceptionForInvalidInventory() {
        Inventory request = Inventory.builder()
                .sku("")
                .quantity(10)
                .build();

        DomainException exception = assertThrows(DomainException.class, () -> createInventory.execute(request));

        assertTrue(exception.getMessage().contains("SKU is required"));
        verify(inventoryGateway, never()).save(any());
        verify(inventoryGateway, never()).update(any());
    }
}