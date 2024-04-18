package com.mscv.inventoryservice.repository;

import com.mscv.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IInventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByCodeSkuIn(List<String> skuCode);
}