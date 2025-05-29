package com.chugs.chugs.mapper;

import com.chugs.chugs.dto.*;
import com.chugs.chugs.entity.Category;
import com.chugs.chugs.entity.Product;
import com.chugs.chugs.entity.Subcategory;
import com.chugs.chugs.repository.CategoryRepository;
import com.chugs.chugs.repository.SizeRepository;
import com.chugs.chugs.repository.SubcategoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ProductMapper {
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final SizeRepository sizeRepository;

    public ProductMapper(CategoryRepository categoryRepository,
                         SubcategoryRepository subcategoryRepository,
                         SizeRepository sizeRepository) {
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.sizeRepository = sizeRepository;
    }

    public Product toEntity(ProductRequestDTO dto){
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found")));
        product.setSubcategory(subcategoryRepository.findById(dto.getSubcategoryId())
                .orElseThrow(() -> new RuntimeException("Subcategory not found")));
        product.setSize(sizeRepository.findById(dto.getSizeId())
                .orElseThrow(() -> new RuntimeException("Size not found")));
        return product;
    }

    public ProductResponseDTO toDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setProductId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());

        if (product.getSize() != null) {
            String quantity = product.getSize().getQuantity().toPlainString();
            String unitName = product.getSize().getUnit().getAbbreviation();
            dto.setSizeWithUnit(quantity + unitName);
        }

        Category category = product.getCategory();
        Subcategory subcategory = product.getSubcategory();

        if (category != null) {
            List<SubcategoryDTO> subcategoryDTOs = category.getSubcategories().stream()
                    .map(sub -> {
                        List<SizeDTO> sizeDTOs = sub.getSizes().stream()
                                .map(size -> new SizeDTO(
                                        size.getId(),
                                        size.getQuantity(),
                                        size.getUnit().getName(),
                                        size.getUnit().getAbbreviation()))
                                .collect(Collectors.toList());

                        List<IngredientDTO> ingredientDTOs = sub.getDefaultIngredients().stream()
                                .map(ingredient -> new IngredientDTO(
                                        ingredient.getIngredient().getInventoryId(),
                                        ingredient.getIngredient().getName(),
                                        ingredient.getQuantity(),
                                        ingredient.getSize().getUnit().getAbbreviation()))
                                .collect(Collectors.toList());

                        return new SubcategoryDTO(sub.getId(), sub.getName(), sizeDTOs, ingredientDTOs);
                    })
                    .collect(Collectors.toList());

            CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getName(), subcategoryDTOs);
            dto.setCategory(categoryDTO);
        }
        return dto;
    }


}
