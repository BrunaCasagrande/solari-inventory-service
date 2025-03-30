package br.com.solari.application.dto;

import br.com.solari.application.domain.Address;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientDto {

  private String name;

  private String phoneNumber;

  private String email;

  private String password;

  private Address address;

}
