package com.micro.inventoryservice.repository;

import com.micro.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsByProductCodeAndQuantityIsGreaterThanEqual(String skuCode, int quantity);
}
