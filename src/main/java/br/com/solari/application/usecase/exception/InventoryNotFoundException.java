package br.com.solari.application.usecase.exception;

public class InventoryNotFoundException extends BusinessException {
  private static final String MESSAGE_ID = "Inventory with id=[%s] not found.";
  private static final String MESSAGE_SKU = "Inventory with sku=[%s] not found.";
  private static final String ERROR_CODE = "INVENTORY_NOT_FOUND";

  public InventoryNotFoundException(final Integer id) {
    super(String.format(MESSAGE_ID, id), ERROR_CODE);
  }

  public InventoryNotFoundException(final String sku) {
    super(String.format(MESSAGE_SKU, sku), ERROR_CODE);
  }
}
