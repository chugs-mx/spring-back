package com.chugs.chugs.repository

import com.chugs.chugs.entity.Inventory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InventoryRepository extends JpaRepository<Inventory, Long>{
    List<Inventory> findByInventoryCategory(Inventory.InventoryCategory inventoryType)
}