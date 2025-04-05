package br.com.solari.infrastructure.presenter.response;

import lombok.Builder;

@Builder
public record InventoryPresenterResponse(
        int id, String sku, Integer quantity) {}
