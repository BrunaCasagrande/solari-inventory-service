package br.com.solari.application.domain;

import br.com.solari.application.domain.exception.DomainException;
import br.com.solari.application.domain.exception.ErrorDetail;
import jakarta.validation.*;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inventory {

  private Integer id;

  @Pattern(regexp = "\\d+", message = "SKU must contain only numbers")
  @NotBlank(message = "SKU is required")
  private String sku;

  @NotNull(message = "Quantity is required")
  @Min(value = 1, message = "Quantity must be one or greater")
  private Integer quantity;

  public static Inventory createInventory(final String sku, final Integer quantity) {
    final var inventory =
            Inventory.builder()
                    .sku(sku)
                    .quantity(quantity)
                    .build();

    validate(inventory);

    return inventory;
  }

  private static void validate(final Inventory inventory) {
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    final Validator validator = factory.getValidator();
    final Set<ConstraintViolation<Inventory>> violations = validator.validate(inventory);

    if (!violations.isEmpty()) {
      final List<ErrorDetail> errors =
              violations.stream()
                      .map(violation ->
                              new ErrorDetail(
                                      violation.getPropertyPath().toString(),
                                      violation.getMessage(),
                                      violation.getInvalidValue()))
                      .toList();

      final String firstErrorMessage = errors.get(0).errorMessage();

      throw new DomainException(firstErrorMessage, "domain_exception", errors);
    }
  }
}
