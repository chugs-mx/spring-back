package com.chugs.chugs.controller;

import com.chugs.chugs.Service.ProductService;
import com.chugs.chugs.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    final private ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService){ this.productService = productService; }

    @GetMapping("")
    ResponseEntity<PagedModel<Product>>getProducts(
            @RequestParam( name = "page", defaultValue = "0" ) int page,
            @RequestParam( name = "size", defaultValue = "10" ) int size,
            @RequestParam( name = "sort", defaultValue = "" ) String sort,
            @RequestParam( name = "asc", defaultValue = "true") boolean asc,
            @RequestParam( name = "category", required = false ) String category,
            @RequestParam( name = "search", required = false ) String search
    ){
        Page<Product> products = productService.getProductsWithPaginationFilteringAndSorting(
                page,
                size,
                search,
                sort,
                asc,
                category
        );
        if( products.isEmpty() ){
            logger.info("[Get] product noy found");
            return ResponseEntity.noContent().build();
        }
        logger.info("[Get] Products found: {}", products.getTotalElements());
        return ResponseEntity.ok(new PagedModel<>(products));
    }

}
