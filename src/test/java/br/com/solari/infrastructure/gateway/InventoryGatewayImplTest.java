package br.com.solari.infrastructure.gateway;

import br.com.solari.application.domain.Inventory;
import br.com.solari.application.gateway.exception.GatewayException;
import br.com.solari.infrastructure.persistence.entity.InventoryEntity;
import br.com.solari.infrastructure.persistence.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryGatewayImplTest {

    private InventoryRepository inventoryRepository;
    private InventoryGatewayImpl inventoryGateway;

    @BeforeEach
    void setUp() {
        inventoryRepository = mock(InventoryRepository.class);
        inventoryGateway = new InventoryGatewayImpl(inventoryRepository);
    }

    @Test
    void shouldSaveInventory() {
        Inventory inventory = Inventory.createInventory("12345", 10);
        InventoryEntity entity = InventoryEntity.builder().sku("12345").quantity(10).build();

        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(entity);

        Inventory result = inventoryGateway.save(inventory);

        assertNotNull(result);
        assertEquals("12345", result.getSku());
        assertEquals(10, result.getQuantity());

        ArgumentCaptor<InventoryEntity> captor = ArgumentCaptor.forClass(InventoryEntity.class);
        verify(inventoryRepository).save(captor.capture());
        InventoryEntity savedEntity = captor.getValue();

        assertEquals("12345", savedEntity.getSku());
        assertEquals(10, savedEntity.getQuantity());
    }

    @Test
    void shouldUpdateInventory() {
        Inventory inventory = Inventory.createInventory("12345", 15);
        inventory.setId(1);
        InventoryEntity entity = InventoryEntity.builder().id(1).sku("12345").quantity(10).build();

        when(inventoryRepository.findById(1)).thenReturn(Optional.of(entity));
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(entity);

        Inventory result = inventoryGateway.update(inventory);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("12345", result.getSku());
        assertEquals(15, result.getQuantity());

        verify(inventoryRepository).save(entity);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentInventory() {
        Inventory inventory = Inventory.createInventory("12345", 15);
        inventory.setId(1);

        when(inventoryRepository.findById(1)).thenReturn(Optional.empty());

        GatewayException exception = assertThrows(
                GatewayException.class,
                () -> inventoryGateway.update(inventory)
        );

        assertEquals("Inventory with id=[1] not found.", exception.getMessage());
    }

    @Test
    void shouldDeleteInventoryById() {
        doNothing().when(inventoryRepository).deleteById(1);

        inventoryGateway.deleteById(1);

        verify(inventoryRepository).deleteById(1);
    }

    @Test
    void shouldFindInventoryById() {
        InventoryEntity entity = InventoryEntity.builder().id(1).sku("12345").quantity(10).build();

        when(inventoryRepository.findById(1)).thenReturn(Optional.of(entity));

        Optional<Inventory> result = inventoryGateway.findById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        assertEquals("12345", result.get().getSku());
        assertEquals(10, result.get().getQuantity());
    }

    @Test
    void shouldFindAllBySku() {
        InventoryEntity entity1 = InventoryEntity.builder().sku("12345").quantity(10).build();
        InventoryEntity entity2 = InventoryEntity.builder().sku("12345").quantity(5).build();

        when(inventoryRepository.findAllBySku("12345")).thenReturn(List.of(entity1, entity2));

        List<Inventory> result = inventoryGateway.findAllBySku("12345");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(10, result.get(0).getQuantity());
        assertEquals(5, result.get(1).getQuantity());
    }
}