package com.chugs.chugs.Service

import com.chugs.chugs.entity.Inventory
import com.chugs.chugs.entity.Product
import com.chugs.chugs.entity.ProductInventory
import com.chugs.chugs.repository.InventoryRepository
import com.chugs.chugs.repository.ProductInventoryRepository
import com.chugs.chugs.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service

@Service
class ProductInventoryService {

    @Autowired
    ProductRepository productRepository

    @Autowired
    InventoryRepository inventoryRepository

    @Autowired
    ProductInventoryRepository productInventoryRepository

    ProductInventory addIngredientToProduct(Long productId, Long inventoryId, BigDecimal quantityUsed){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."))
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found."))

        ProductInventory productInventory = new ProductInventory()
        productInventory.setProductId(product)
        productInventory.setInventory(inventory)
        productInventory.setQuantity(quantityUsed)
        println("Product inventory quantity: "+productInventory.quantity)

        return productInventoryRepository.save(productInventory)
    }

    List<Inventory> getIngredientsByProduct(Long productId){
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found."))

        return productInventoryRepository.findByProduct(product)
    }

    ProductInventory updateIngredientQuantity(Long productId, Long inventoryId, BigDecimal quantity){
        ProductInventory productInventory = productInventoryRepository.findByProductAndInventory(
                productRepository.findById(productId).orElseThrow(),
                inventoryRepository.findById(inventoryId).orElseThrow()
        ).orElseThrow( () -> new RuntimeException("Not found."))

        println("After quantity: "+productInventory.quantity)
        productInventory.setQuantity(quantity)
        println("New quantity: "+quantity)
        return productInventoryRepository.save(productInventory)
    }

    void removeIngredientFromProduct(Long productId, Long inventoryId){
        ProductInventory productInventory = productInventoryRepository.findByProductAndInventory(
                productRepository.findById(productId).orElseThrow(),
                inventoryRepository.findById(inventoryId).orElseThrow()
        ).orElseThrow( () -> new RuntimeException("Not found."))

        productInventoryRepository.delete(productInventory)
    }
}
