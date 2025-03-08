package com.chugs.chugs.Service

import com.chugs.chugs.entity.Inventory
import com.chugs.chugs.entity.Ticket
import com.chugs.chugs.repository.InventoryRepository
import spock.lang.Specification

import java.time.LocalDateTime

class InventoryServiceSpec extends Specification {

    InventoryRepository inventoryRepository = Mock()

    InventoryService inventoryService = new InventoryService(inventoryRepository: inventoryRepository)

    def "springTest"() {
        given: "some add ingredient"
        Inventory inventory = new Inventory(
                name: "Onion",
                inventoryCategory: Inventory.InventoryCategory.REFRIGERATED,
                subcategory: Inventory.Subcategory.VEGETABLES,
                description: "For hamburgers",
                entryDate: LocalDateTime.now(),
                expiryDate: LocalDateTime.now().plusMonths(1),
                unitMeasure: Inventory.UnitMeasure.KG,
                unitPrice: BigDecimal.valueOf(10.5),
                quantity: BigDecimal.valueOf(5)
        )

        println("Inventory to persist: " + inventory.name)

        inventoryRepository.save(_) >> { Inventory inv ->
            inv.inventoryId = 1L
            return inv
        }

        when: "fetch inventory from DB"
        def foundInventory = inventoryService.addToInventory(inventory)

        then: "check if inventory exists"
        foundInventory != null
        foundInventory.name == "Onion"
    }


    def"testAddInventoryWithoutCategory"(){
        given:
            Inventory inventory = new Inventory(
                    name: "Vegan hamburger",
                    inventoryCategory: missingCategory,
                    subcategory: missingSubcategory,
                    description: "For hamburgers",
                    entryDate: LocalDateTime.now(),
                    expiryDate: LocalDateTime.now().plusMonths(1),
                    unitPrice: BigDecimal.valueOf(10.5),
                    unitMeasure: missingUnitMeasure,
                    quantity: BigDecimal.valueOf(5)
            )
        when: "the inventory is saved"
        inventoryService.addToInventory(inventory)

        then: "an exception should be thrown"
        def e = thrown(IllegalArgumentException)
        assert e.message in ["Inventory category is required.", "Subcategory is required.", "Unit measure is required."]

        where:
        missingCategory | missingSubcategory | missingUnitMeasure
        null           | Inventory.Subcategory.VEGETABLES | Inventory.UnitMeasure.KG
        Inventory.InventoryCategory.REFRIGERATED | null | Inventory.UnitMeasure.KG
        Inventory.InventoryCategory.REFRIGERATED | Inventory.Subcategory.VEGETABLES | null
    }

    def "testInventoryNameNull"() {
        given: "inventory with name null"
        Inventory inventory = new Inventory(
                name: invalidName,
                inventoryCategory: Inventory.InventoryCategory.REFRIGERATED,
                subcategory: Inventory.Subcategory.VEGETABLES,
                description: "Fresh product",
                entryDate: LocalDateTime.now(),
                expiryDate: LocalDateTime.now().plusMonths(1),
                unitMeasure: Inventory.UnitMeasure.KG,
                unitPrice: BigDecimal.valueOf(5.0),
                quantity: BigDecimal.valueOf(10)
        )

        when: "the inventory is saved"
        inventoryService.addToInventory(inventory)

        then: "an exception should be thrown"
        def e = thrown(IllegalArgumentException)
        e.message == "Inventory name is required and must be at most 100 characters."

        where:
        invalidName << [null, "A".repeat(101)]
    }

    def "testShouldThrowErrorForInvalidPriceOrQuantity"() {
        given: "an inventory with invalid values"
        Inventory inventory = new Inventory(
                name: "Tomato",
                inventoryCategory: Inventory.InventoryCategory.REFRIGERATED,
                subcategory: Inventory.Subcategory.VEGETABLES,
                description: "Fresh tomatoes",
                entryDate: LocalDateTime.now(),
                expiryDate: LocalDateTime.now().plusMonths(1),
                unitMeasure: Inventory.UnitMeasure.KG,
                unitPrice: invalidPrice,
                quantity: invalidQuantity
        )

        when: "trying to save the inventory"
        inventoryService.addToInventory(inventory)

        then: "an exception should be thrown"
        def e = thrown(IllegalArgumentException)
        assert e.message in ["Unit price must be greater than zero.", "Quantity must be zero."]

        where:
        invalidPrice            | invalidQuantity
        BigDecimal.valueOf(-5.0) | BigDecimal.valueOf(10)
        BigDecimal.valueOf(5.0)  | BigDecimal.valueOf(-2)
    }



}