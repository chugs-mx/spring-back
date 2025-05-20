package com.chugs.chugs.controller;

import com.chugs.chugs.Service.InventoryService;
import com.chugs.chugs.entity.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventories")
public class InventoryController {
    final private InventoryService inventoryService;
    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{id}")
    ResponseEntity<Inventory> getInventory(@PathVariable("id") Long id) {
        Inventory inventory = inventoryService.getInventory(id);
        logger.info("[Get] Inventory found: {}", inventory.getInventoryId());
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("")
    ResponseEntity<PagedModel<Inventory>> getInventories(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "") String sort,
            @RequestParam(name = "asc", defaultValue = "true") boolean asc,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "subcategory", required = false) String subCategory,
            @RequestParam(name = "search", required = false) String search
    ) {
        Page<Inventory> invetories = inventoryService.getInventoriesWithPaginationSortingAndFiltering(
                page,
                size,
                search,
                sort,
                asc,
                category,
                subCategory
        );
        if (invetories.isEmpty()) {
            logger.info("[Get] No inventories found");
            return ResponseEntity.noContent().build();
        }
        logger.info("[Get] Inventories found: {}", invetories.getTotalElements());
        return ResponseEntity.ok(new PagedModel<>(invetories));
    }

    @PostMapping("")
    ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        Inventory createdInventory = inventoryService.createInventory(inventory);
        logger.info("[Post] Inventory created: {}", createdInventory.getInventoryId());
        return ResponseEntity.ok(createdInventory);
    }

    @PutMapping("/{id}")
    ResponseEntity<Inventory> updateInventory(@PathVariable("id") Long id, @RequestBody Inventory inventory) {
        Inventory updatedInventory = inventoryService.updateInventory(id, inventory);
        logger.info("[Put] Inventory updated: {}", updatedInventory.getInventoryId());
        return ResponseEntity.ok(updatedInventory);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Inventory> deleteInventory(@PathVariable("id") Long id) {
        var deletedInventory = inventoryService.deleteInventory(id);
        logger.info("[Delete] Inventory deleted: {}", id);
        return ResponseEntity.accepted().body(deletedInventory);
    }

    @PatchMapping("/{id}")
    ResponseEntity<Inventory> patchInventory(@PathVariable("id") Long id, @RequestBody Inventory inventory) {
        Inventory patchedInventory = inventoryService.patchInventory(id, inventory);
        logger.info("[Patch] Inventory patched: {}", patchedInventory.getInventoryId());
        return ResponseEntity.ok(patchedInventory);
    }
/**
    @GetMapping("/categories")
    ResponseEntity<Inventory.InventoryCategory[]> getInventoryCategories() {
        logger.info("[Get] Categories found: {}", Inventory.InventoryCategory.values().length);
        return ResponseEntity.ok(Inventory.InventoryCategory.values());
    }

    @GetMapping("/subcategories")
    ResponseEntity<Inventory.Subcategory[]> getInventorySubCategories() {
        logger.info("[Get] Subcategories found: {}", Inventory.Subcategory.values().length);
        return ResponseEntity.ok(Inventory.Subcategory.values());
    }
    **/
}