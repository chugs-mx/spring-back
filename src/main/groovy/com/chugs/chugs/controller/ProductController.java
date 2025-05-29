package com.chugs.chugs.controller;

import com.chugs.chugs.Service.ProductService;
import com.chugs.chugs.dto.*;
import com.chugs.chugs.entity.Product;
import com.chugs.chugs.entity.Subcategory;
import com.chugs.chugs.mapper.ProductMapper;
import com.chugs.chugs.repository.SubcategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    final private ProductService productService;
    final private ProductMapper productMapper;
    final private SubcategoryRepository subcategoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService, ProductMapper productMapper, SubcategoryRepository subcategoryRepository) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.subcategoryRepository = subcategoryRepository;
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductResponseDTO> getProduct(@PathVariable("id") Long id){
        Product product = productService.getProduct(id);
        logger.info("[Get] product found {}", product.getId());
        ProductResponseDTO dto = productMapper.toDTO(product);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("")
    ResponseEntity<Page<ProductResponseDTO>>getProducts(
            @RequestParam( name = "page", defaultValue = "0" ) int page,
            @RequestParam( name = "size", defaultValue = "10" ) int size,
            @RequestParam( name = "sort", defaultValue = "" ) String sort,
            @RequestParam( name = "asc", defaultValue = "true") boolean asc,
            @RequestParam( name = "category", required = false ) String category,
            @RequestParam( name = "subcategory", required = false ) String subcategory,
            @RequestParam( name = "search", required = false ) String search
    ){
        Page<ProductResponseDTO> products = productService.getProductsDTOWithPaginationFilteringAndSorting(
                page,
                size,
                search,
                sort,
                asc,
                category,
                subcategory
        );
        if( products.isEmpty() ){
            logger.info("[Get] product noy found");
            return ResponseEntity.noContent().build();
        }
        logger.info("[Get] Products found: {}", products.getTotalElements());
        return ResponseEntity.ok(products);
    }

    @PostMapping("")
    ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO dto){
        //Product product = productMapper.toEntity(dto);
        Product createdProduct = productService.createProduct(dto);
        ProductResponseDTO responseDTO = productMapper.toDTO(createdProduct);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ProductResponseDTO> deleteProduct(@PathVariable("id") Long id) {
        var deletedProduct = productService.deleteProduct(id);
        logger.info("[Delete] Product deleted: {}", id);
        ProductResponseDTO deletedProductDTO = productMapper.toDTO(deletedProduct);
        return ResponseEntity.accepted().body(deletedProductDTO);
    }

    @PatchMapping("/{id}")
    ResponseEntity<ProductResponseDTO> patchProduct(@PathVariable("id") Long id, @RequestBody Product product){
        Product patchedProduct = productService.patchProduct(id, product);
        logger.info("[PATCH] Product patched: {}", patchedProduct.getId());
        return ResponseEntity.ok(productMapper.toDTO(patchedProduct));
    }

    @GetMapping("/categories")
    ResponseEntity<List<CategoryDTO>> getProductCategories(){
        List<CategoryDTO> categories = productService.getProductCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/subcategories")
    public ResponseEntity<List<SubcategoryDTO>> getSubcategories(){
        List<Subcategory> subcategories = subcategoryRepository.findAll();

        List<SubcategoryDTO> response = subcategories.stream()
                .map(subcategory -> {
                    List<SizeDTO> sizeDTOS = subcategory.getSizes().stream().map(size -> new SizeDTO(
                            size.getId(),
                            size.getQuantity(),
                            size.getUnit().getName(),
                            size.getUnit().getAbbreviation()
                    )).toList();
                    List<IngredientDTO> ingredientDTOS = subcategory.getDefaultIngredients().stream().map(defIng -> new IngredientDTO(
                            defIng.getIngredient().getInventoryId(),
                            defIng.getIngredient().getName(),
                            defIng.getQuantity(),
                            defIng.getSize().getUnit().getAbbreviation()
                    )).toList();

                    return new SubcategoryDTO(
                            subcategory.getId(),
                            subcategory.getName(),
                            sizeDTOS,
                            ingredientDTOS
                    );
                })
                .toList();
        return ResponseEntity.ok(response);
    }

}
