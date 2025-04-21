package com.chugs.chugs.controller;

import com.chugs.chugs.Service.InventoryService;
import com.chugs.chugs.entity.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventories")
public class InventoryController {
    final private InventoryService inventoryService;
    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    @GetMapping("/")
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

}
