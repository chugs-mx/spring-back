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
                    description: "For hamburgers",
                    entryDate: LocalDateTime.now(),
                    expiryDate: LocalDateTime.now().plusMonths(1),
                    unitPrice: BigDecimal.valueOf(10.5),
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
}