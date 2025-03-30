package br.com.solari.application.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

  private Integer id;

  @NotBlank private String street;
  @NotBlank private String number;
  @NotBlank private String city;
  @NotBlank private String state;
  @NotBlank private String zipCode;
}
