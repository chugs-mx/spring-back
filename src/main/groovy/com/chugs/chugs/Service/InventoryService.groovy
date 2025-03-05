package com.chugs.chugs.Service

import com.chugs.chugs.entity.Inventory
import com.chugs.chugs.repository.InventoryRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InventoryService {
    @Autowired
    InventoryRepository inventoryRepository

    @Transactional
    Inventory addToInventory(Inventory inventory){
        if( !inventory.name || inventory.name.length() > 100 ){
            throw new IllegalArgumentException("Inventory name is required and must be at most 100 characters.")
        }
        if( !inventory.inventoryCategory ){
            throw new IllegalArgumentException("Inventory category is required.")
        }
        if( !inventory.subcategory ){
            throw new IllegalArgumentException("Subcategory is required.")
        }
        if( !inventory.description || inventory.description.length() > 500 ){
            throw new IllegalArgumentException("Description is required and must be at most 500 characters.")
        }
        if( inventory.expiryDate && inventory.expiryDate.isBefore(inventory.entryDate) ){
            throw new IllegalArgumentException("Expiry date must be after the entry date.")
        }
        if( !inventory.unitMeasure ){
            throw new IllegalArgumentException("Unit measure is required.")
        }
        if( inventory.unitPrice == null || inventory.unitPrice.compareTo(BigDecimal.ZERO) <= 0 ) {
            throw new IllegalArgumentException("Unit price must be greater than zero.")
        }
        if( inventory.quantity == null || inventory.quantity.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Quantity must be zero.")
        }

        return inventoryRepository.save(inventory)
    }

    List<Inventory> inventoriesByCategory(Inventory.InventoryCategory category){
        if( category == null ){
            throw new IllegalArgumentException("Category is null.")
        }
        return inventoryRepository.findByInventoryCategory(category)
    }


}
