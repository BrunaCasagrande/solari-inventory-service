package br.com.solari.infrastructure.controller;

import br.com.solari.application.domain.Inventory;
import br.com.solari.application.dto.UpdateInventoryDto;
import br.com.solari.application.usecase.*;
import br.com.solari.infrastructure.presenter.InventoryPresenter;
import br.com.solari.infrastructure.presenter.response.InventoryPresenterResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/solari/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

  private final CreateInventory createInventory;
  private final DeleteInventory deleteInventory;
  private final SearchInventory searchInventory;
  private final UpdateInventory updateInventory;
  private final FindInventoryById findInventoryById;

  private final InventoryPresenter inventoryPresenter;

  @PostMapping
  public ResponseEntity<InventoryPresenterResponse> create(@Valid @RequestBody final Inventory request) {
    final var created = this.createInventory.execute(request);

    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(created.getId())
            .toUri();

    return ResponseEntity.created(location).body(inventoryPresenter.parseToResponse(created));
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<InventoryPresenterResponse> findById(@PathVariable final Integer id) {
    final var inventory = this.findInventoryById.execute(id);
    return ResponseEntity.ok(inventoryPresenter.parseToResponse(inventory));
  }
//transformar a lista num resultado unico
@GetMapping("/sku/{sku}")
public ResponseEntity<List<InventoryPresenterResponse>> findAllBySku(@PathVariable final String sku) {
  final var inventories = this.searchInventory.execute(sku);
  final var response = inventories.stream()
          .map(inventoryPresenter::parseToResponse)
          .collect(Collectors.toList());

  return ResponseEntity.ok(response);
}

  @PutMapping("/sku/{sku}")
  public ResponseEntity<List<InventoryPresenterResponse>> updateBySku(
          @PathVariable final String sku,
          @Valid @RequestBody final UpdateInventoryDto request
  ) {
    final var updatedList = this.updateInventory.execute(sku, request);

    final var response = updatedList.stream()
            .map(inventoryPresenter::parseToResponse)
            .collect(Collectors.toList());

    return ResponseEntity.ok(response);
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable final Integer id) {
    this.deleteInventory.execute(id);
    return ResponseEntity.noContent().build();
  }
}
