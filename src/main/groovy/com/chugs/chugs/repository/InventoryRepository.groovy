package com.chugs.chugs.repository

import com.chugs.chugs.entity.Inventory
import org.springframework.data.domain.Page
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Pageable;

@Repository
interface InventoryRepository extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory>{
    List<Inventory> findByInventoryCategory(Inventory.InventoryCategory inventoryType)
    Page<Inventory> findAll(Specification<Inventory> specification, Pageable pageable)
}