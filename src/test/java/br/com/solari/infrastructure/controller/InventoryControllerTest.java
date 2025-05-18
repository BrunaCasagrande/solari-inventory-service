package br.com.solari.infrastructure.controller;

import br.com.solari.application.domain.Inventory;
import br.com.solari.application.dto.UpdateInventoryDto;
import br.com.solari.application.usecase.*;
import br.com.solari.infrastructure.presenter.InventoryPresenter;
import br.com.solari.infrastructure.presenter.response.InventoryPresenterResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryControllerTest {

    private CreateInventory createInventory;
    private DeleteInventory deleteInventory;
    private SearchInventory searchInventory;
    private UpdateInventory updateInventory;
    private FindInventoryById findInventoryById;
    private InventoryPresenter inventoryPresenter;
    private InventoryController inventoryController;

    @BeforeEach
    void setUp() {
        createInventory = mock(CreateInventory.class);
        deleteInventory = mock(DeleteInventory.class);
        searchInventory = mock(SearchInventory.class);
        updateInventory = mock(UpdateInventory.class);
        findInventoryById = mock(FindInventoryById.class);
        inventoryPresenter = mock(InventoryPresenter.class);
        inventoryController = new InventoryController(
                createInventory, deleteInventory, searchInventory, updateInventory, findInventoryById, inventoryPresenter
        );
    }

    @Test
    void shouldCreateInventory() {
        // Simula o contexto de requisição HTTP com o caminho correto
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/solari/v1/inventory");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Inventory requestInventory = Inventory.createInventory("12345", 10);
        Inventory createdInventory = Inventory.createInventory("12345", 10);
        createdInventory.setId(1);
        InventoryPresenterResponse response = new InventoryPresenterResponse(1, "12345", 10);

        when(createInventory.execute(requestInventory)).thenReturn(createdInventory);
        when(inventoryPresenter.parseToResponse(createdInventory)).thenReturn(response);

        ResponseEntity<InventoryPresenterResponse> result = inventoryController.create(requestInventory);

        assertNotNull(result);
        assertEquals(201, result.getStatusCodeValue());
        assertEquals("/1", result.getHeaders().getLocation().getPath());
        assertEquals(response, result.getBody());
    }

    @Test
    void shouldFindInventoryById() {
        Inventory inventory = Inventory.createInventory("12345", 10);
        inventory.setId(1);
        InventoryPresenterResponse response = new InventoryPresenterResponse(1, "12345", 10);

        when(findInventoryById.execute(1)).thenReturn(inventory);
        when(inventoryPresenter.parseToResponse(inventory)).thenReturn(response);

        ResponseEntity<InventoryPresenterResponse> result = inventoryController.findById(1);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(response, result.getBody());
    }

    @Test
    void shouldFindAllBySku() {
        String sku = "12345";
        Inventory inventory1 = Inventory.createInventory(sku, 10);
        Inventory inventory2 = Inventory.createInventory(sku, 5);
        InventoryPresenterResponse response1 = new InventoryPresenterResponse(1, sku, 10);
        InventoryPresenterResponse response2 = new InventoryPresenterResponse(2, sku, 5);

        when(searchInventory.execute(sku)).thenReturn(List.of(inventory1, inventory2));
        when(inventoryPresenter.parseToResponse(inventory1)).thenReturn(response1);
        when(inventoryPresenter.parseToResponse(inventory2)).thenReturn(response2);

        ResponseEntity<List<InventoryPresenterResponse>> result = inventoryController.findAllBySku(sku);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(List.of(response1, response2), result.getBody());
    }

    @Test
    void shouldUpdateInventoryBySku() {
        String sku = "12345";
        UpdateInventoryDto request = new UpdateInventoryDto(5);
        Inventory inventory1 = Inventory.createInventory(sku, 10);
        Inventory inventory2 = Inventory.createInventory(sku, 15);
        InventoryPresenterResponse response1 = new InventoryPresenterResponse(1, sku, 15);
        InventoryPresenterResponse response2 = new InventoryPresenterResponse(2, sku, 20);

        when(updateInventory.execute(sku, request)).thenReturn(List.of(inventory1, inventory2));
        when(inventoryPresenter.parseToResponse(inventory1)).thenReturn(response1);
        when(inventoryPresenter.parseToResponse(inventory2)).thenReturn(response2);

        ResponseEntity<List<InventoryPresenterResponse>> result = inventoryController.updateBySku(sku, request);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(List.of(response1, response2), result.getBody());
    }

    @Test
    void shouldDeleteInventoryById() {
        doNothing().when(deleteInventory).execute(1);

        ResponseEntity<Void> result = inventoryController.delete(1);

        assertNotNull(result);
        assertEquals(204, result.getStatusCodeValue());
        verify(deleteInventory).execute(1);
    }
}