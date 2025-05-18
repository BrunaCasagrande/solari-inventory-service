package br.com.solari.application.domain;

import br.com.solari.application.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @Test
    void shouldCreateValidInventory() {
        Inventory inventory = Inventory.createInventory("12345", 10);
        assertNotNull(inventory);
        assertEquals("12345", inventory.getSku());
        assertEquals(10, inventory.getQuantity());
    }

    @Test
    void shouldThrowExceptionForInvalidSku() {
        DomainException exception = assertThrows(DomainException.class, () ->
                Inventory.createInventory("abc", 10));
        assertTrue(exception.getMessage().contains("SKU must contain only numbers"));
    }

    @Test
    void shouldThrowExceptionForInvalidQuantity() {
        DomainException exception = assertThrows(DomainException.class, () ->
                Inventory.createInventory("12345", 0));
        assertTrue(exception.getMessage().contains("Quantity must be one or greater"));
    }

    @Test
    void shouldThrowExceptionForNullQuantity() {
        DomainException exception = assertThrows(DomainException.class, () ->
                Inventory.createInventory("12345", null));
        assertTrue(exception.getMessage().contains("Quantity is required"));
    }
}