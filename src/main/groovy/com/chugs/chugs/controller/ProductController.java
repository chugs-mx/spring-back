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

    @GetMapping("/{id}")
    ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        Product product = productService.getProduct(id);
        logger.info("[Get] product found {}", product.getProductId());
        return ResponseEntity.ok(product);
    }

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

    @PostMapping("")
    ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product createProduct = productService.createProduct(product);
        logger.info("[POST] create product: {}", createProduct.getProductId());
        return ResponseEntity.ok(createProduct);
    }

    @PutMapping("/{id}")
    ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        Product updateProduct = productService.updateProduct(id, product);
        logger.info("[PUT] update product: {}", updateProduct.getProductId());
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        var deletedProduct = productService.deleteProduct(id);
        logger.info("[Delete] Product deleted: {}", id);
        return ResponseEntity.accepted().body(deletedProduct);
    }

    @PatchMapping("/{id}")
    ResponseEntity<Product> patchProduct(@PathVariable("id") Long id, @RequestBody Product product){
        Product patchedProduct = productService.patchProduct(id, product);
        logger.info("[PATCH] Product patched: {}", patchedProduct.getProductId());
        return ResponseEntity.ok(patchedProduct);
    }


}
