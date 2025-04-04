package com.chugs.chugs.Service

import com.chugs.chugs.entity.Inventory
import com.chugs.chugs.entity.Product
import com.chugs.chugs.entity.ProductInventory
import com.chugs.chugs.repository.InventoryRepository
import com.chugs.chugs.repository.ProductInventoryRepository
import com.chugs.chugs.repository.ProductRepository
import spock.lang.Specification

class ProductInventoryServiceSpec extends Specification{
    ProductRepository productRepository = Mock()
    InventoryRepository inventoryRepository = Mock()
    ProductInventoryRepository productInventoryRepository = Mock()

    ProductInventoryService productInventoryService = new ProductInventoryService(productRepository: productRepository, inventoryRepository: inventoryRepository, productInventoryRepository: productInventoryRepository)

    def"testAddIngredient"(){
        given:
        Long inventoryId = 11L
        Long productId = 22L
        BigDecimal quantity = BigDecimal.valueOf(3)

        Product product = new Product(productId: productId, name: "Burger")
        Inventory inventory =  new Inventory(inventoryId: inventoryId, name: "Cheese")

        productRepository.findById(productId) >> Optional.of(product)
        inventoryRepository.findById(inventoryId) >> Optional.of(inventory)
        productInventoryRepository.save(_) >> { ProductInventory pi -> pi }

        when: "adding an ingredient to the product"
        ProductInventory result = productInventoryService.addIngredientToProduct(productId, inventoryId, quantity)

        then: "return the saved ProductInventory"
        result != null
        result.productId == product
        result.inventory == inventory
        result.quantity == quantity
    }

    def"testAddIngredientToProductWhenProductNotFound"(){
        given:
        Long inventoryId = 11L
        Long productId = 22L

        productRepository.findById(productId) >> Optional.empty()

        when:
        productInventoryService.addIngredientToProduct(productId, inventoryId, BigDecimal.ONE)

        then:
        thrown(RuntimeException)
    }

    /*
    def"testGetIngredientByProduct"(){
        given:
        Long productId = 22L
        Product product = new Product(productId: productId, name: "Hamburger")

        Inventory ingredient1 = new Inventory(inventoryId:1L, name: "Meat")
        Inventory ingredient2 = new Inventory(inventoryId: 2L, name: "Bread")

        List<ProductInventory> productInventoryList = [
                new ProductInventory(productId: product, inventory: ingredient1, quantity: BigDecimal.valueOf(1)),
                new ProductInventory(productId: product, inventory: ingredient2, quantity: BigDecimal.valueOf(2))
        ]
        productRepository.findById(productId) >> Optional.of(product)
        productInventoryRepository.findByProduct(product) >> productInventoryList

        when:
        List<ProductInventory> ingredients = productInventoryService.getIngredientsByProduct(productId)

        then:
        ingredients.size() == 2
        ingredients*.inventory.name.containsAll(["Meat", "Bread"])
    }
    */

    def"testUpdateIngredientQuantity"(){
        given: "an existing ProductInventory"
        Long productId = 1L
        Long inventoryId = 2L
        BigDecimal newQuantity = BigDecimal.valueOf(5)

        Product product = new Product(productId: productId, name: "Burger")
        Inventory inventory = new Inventory(inventoryId: inventoryId, name: "Lettuce")

        ProductInventory productInventory = new ProductInventory(productId: product, inventory: inventory, quantity: BigDecimal.valueOf(2))

        productRepository.findById(productId) >> Optional.of(product)
        inventoryRepository.findById(inventoryId) >> Optional.of(inventory)
        productInventoryRepository.findByProductIdAndInventory(product, inventory) >> Optional.of(productInventory)
        productInventoryRepository.save(_) >> { ProductInventory p -> p }

        when: "updating the quantity of an ingredient"
        println "Product: " + product
        println "Inventory: " + inventory
        println "ProductInventory found: " + productInventoryRepository.findByProductIdAndInventory(product, inventory)

        ProductInventory updatedInventory = productInventoryService.updateIngredientQuantity(productId, inventoryId, newQuantity)

        println "Updated inventory: " + updatedInventory

        then:
        updatedInventory.quantity == newQuantity
    }

    def "removeIngredientFromProduct"() {
        given:
        Long productId = 1L
        Long inventoryId = 2L

        def product = new Product(productId: productId, name: "Smoothie")
        def inventory = new Inventory(inventoryId: inventoryId, name: "Milk")

        def productInventory = new ProductInventory(productId: product, inventory: inventory, quantity: BigDecimal.valueOf(2))

        productRepository.findById(productId) >> Optional.of(product)
        inventoryRepository.findById(inventoryId) >> Optional.of(inventory)
        productInventoryRepository.findByProductIdAndInventory(product, inventory) >> Optional.of(productInventory)

        when:
        productInventoryService.removeIngredientFromProduct(productId, inventoryId)

        then:
        1 * productInventoryRepository.delete(productInventory)
    }
}
