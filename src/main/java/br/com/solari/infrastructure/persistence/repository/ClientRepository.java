package br.com.solari.infrastructure.persistence.repository;

import br.com.solari.infrastructure.persistence.entity.ClientEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {

  Optional<ClientEntity> findByCpf(final String cpf);

  void deleteByCpf(String cpf);
}
