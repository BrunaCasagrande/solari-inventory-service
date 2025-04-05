package br.com.solari.infrastructure.persistence.repository;

import br.com.solari.infrastructure.persistence.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Integer> {

  List<InventoryEntity> findAllBySku(String sku);
}
