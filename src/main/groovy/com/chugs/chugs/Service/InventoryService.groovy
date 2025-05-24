package com.chugs.chugs.Service

import com.chugs.chugs.entity.Inventory
import com.chugs.chugs.repository.InventoryRepository
import com.chugs.chugs.repository.specification.InventorySpecification
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class InventoryService {
    @Autowired
    InventoryRepository inventoryRepository

    public static final Logger logger = LoggerFactory.getLogger(this.class);

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

        println("Inventory detalles: $inventory")
        def inventorySave = inventoryRepository.save(inventory)

        println("Inventory save: " +inventorySave.inventoryId)
        return inventorySave
    }

    Inventory createInventory( Inventory inventory){
        logger.info("Creating inventory: $inventory")
        return inventoryRepository.save(inventory)
    }
/**
    List<Inventory> inventoriesByCategory(Inventory.InventoryCategory category){
        if( category == null ){
            throw new IllegalArgumentException("Category is null.")
        }
        return inventoryRepository.findByInventoryCategory(category)

    }
**/
    Page<Inventory> getInventoriesWithPaginationSortingAndFiltering(int page, int size, String search, String sort, boolean asc , String category, String subCategory) {

        Sort sortOrder = sort ?  (asc ? Sort.by(sort).ascending() : Sort.by(sort).descending() ) : Sort.unsorted()
        PageRequest pageRequest = PageRequest.of(page, size, sortOrder)
        Specification<Inventory> spec = InventorySpecification.getSpecification(search, category, subCategory)
        Page<Inventory> inventoryPage = inventoryRepository.findAll(spec, pageRequest)
        return inventoryPage
    }

    Inventory updateInventory(long id, Inventory inventory) {

        Optional<Inventory> optionalExistingInventory = inventoryRepository.findById(id)
        if (optionalExistingInventory.isEmpty()) {
            logger.error("Inventory not found with id: $id")
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found with id: $id")
        } else {
            logger.info("Inventory found with id: $id")
        }
        Inventory existingInventory = optionalExistingInventory.get();
        if (inventory.name) {
            existingInventory.name = inventory.name
        }
        if (inventory.inventoryCategory) {
            existingInventory.inventoryCategory = inventory.inventoryCategory
        }
        if (inventory.subcategory) {
            existingInventory.subcategory = inventory.subcategory
        }
        if (inventory.description) {
            existingInventory.description = inventory.description
        }
        if (inventory.entryDate) {
            existingInventory.entryDate = inventory.entryDate
        }
        if (inventory.expiryDate) {
            existingInventory.expiryDate = inventory.expiryDate
        }
        if (inventory.unitMeasure) {
            existingInventory.unitMeasure = inventory.unitMeasure
        }
        if (inventory.unitPrice) {
            existingInventory.unitPrice = inventory.unitPrice
        }
        if (inventory.quantity) {
            existingInventory.quantity = inventory.quantity
        }
        return inventoryRepository.save(existingInventory)
    }

    Inventory deleteInventory(long id) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id)
        if (optionalInventory.isPresent()) {
            Inventory inventory = optionalInventory.get()
            inventoryRepository.delete(inventory)
            return inventory
        } else {
            logger.error("Inventory not found with id: $id")
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found with id: $id")
        }
    }

    Inventory patchInventory(long id, Inventory inventory) {
        Optional<Inventory> optionalExistingInventory = inventoryRepository.findById(id)
        if (optionalExistingInventory.isEmpty()) {
            logger.error("Inventory not found with id: $id")
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found with id: $id")
        } else {
            logger.info("Inventory found with id: $id")
        }
        Inventory existingInventory = optionalExistingInventory.get();
        if (inventory.name) {
            existingInventory.name = inventory.name
        }
        if (inventory.inventoryCategory) {
            existingInventory.inventoryCategory = inventory.inventoryCategory
        }
        if (inventory.subcategory) {
            existingInventory.subcategory = inventory.subcategory
        }
        if (inventory.description) {
            existingInventory.description = inventory.description
        }
        if (inventory.entryDate) {
            existingInventory.entryDate = inventory.entryDate
        }
        if (inventory.expiryDate) {
            existingInventory.expiryDate = inventory.expiryDate
        }
        if (inventory.unitMeasure) {
            existingInventory.unitMeasure = inventory.unitMeasure
        }
        if (inventory.unitPrice) {
            existingInventory.unitPrice = inventory.unitPrice
        }
        if (inventory.quantity) {
            existingInventory.quantity = inventory.quantity
        }
        return inventoryRepository.save(existingInventory)
    }

    Inventory getInventory(long id) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id)
        if (optionalInventory.isEmpty()) {
            logger.info("Inventory not found with id: $id")
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found with id: $id")
        } else {
            return optionalInventory.get()
        }
    }
}
