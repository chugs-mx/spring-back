package com.chugs.chugs.Service

import com.chugs.chugs.dto.CategoryDTO
import com.chugs.chugs.dto.IngredientDTO
import com.chugs.chugs.dto.ProductResponseDTO
import com.chugs.chugs.dto.SizeDTO
import com.chugs.chugs.dto.SubcategoryDTO
import com.chugs.chugs.entity.Category
import com.chugs.chugs.entity.Product
import com.chugs.chugs.mapper.ProductMapper
import com.chugs.chugs.repository.CategoryRepository
import com.chugs.chugs.repository.ProductRepository
import com.chugs.chugs.repository.specification.ProductSpecification
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.springframework.data.domain.Pageable;


@Service
class ProductService {

    @Autowired
    ProductRepository productRepository
    CategoryRepository categoryRepository

    private final ProductMapper productMapper


    public static final Logger logger = LoggerFactory.getLogger(this.class)

    ProductService(ProductMapper productMapper, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productMapper = productMapper
        this.productRepository = productRepository
        this.categoryRepository = categoryRepository
    }

    public Product createProduct(Product product) {
        productRepository.findById(product.productId).ifPresent {
            logger.error("Product already exist")
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product already exist")
        }
        return productRepository.save(product)
    }

    Product deleteProduct(Long id) {
        Optional<Product> optionalExistingProduct = productRepository.findById(id)
        if (optionalExistingProduct.isPresent()) {
            Product product = optionalExistingProduct.get()
            productRepository.delete(product)
            return product
        } else {
            logger.error("Product not found with id: $id")
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: $id")
        }
    }

    List<ProductResponseDTO> getAllProductsDTOs() {
        List<Product> products = productRepository.findAll()
        return products.collect { product ->
            CategoryDTO categoryDTO = new CategoryDTO(
                    product.category.id,
                    product.category.name,
                    List.of(
                            new SubcategoryDTO(product.subcategory.id, product.subcategory.name)
                    )
            )
            String unitName = product.size.unit.abbreviation
            String sizeWithUnit = product.getSize().getQuantity().toPlainString() + "" + unitName
            SizeDTO sizeDTO = new SizeDTO(
                    product.size.id,
                    product.size.quantity,
                    product.size.unit.name,
                    product.size.unit.abbreviation
            )
            return new ProductResponseDTO(
                    product.productId,
                    product.name,
                    product.description,
                    product.price,
                    sizeWithUnit,
                    categoryDTO,
                    new SubcategoryDTO(product.subcategory.id, product.subcategory.name)
            )
        }
    }

    Page<ProductResponseDTO> getProductsDTOWithPaginationFilteringAndSorting(int page,
                                                                             int size,
                                                                             String search,
                                                                             String sort,
                                                                             boolean asc,
                                                                             String category,
                                                                             String subcategory
    ) {
        List<String> allowedSortFields = List.of("name", "price", "productId");

        String sortField = (sort == null || sort.isBlank() || !allowedSortFields.contains(sort))
                ? "name"
                : sort;

        Sort sortOrder = asc
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        Specification<Product> spec = ProductSpecification.getSpecification(search, category, subcategory);
        Page<Product> productsPage = productRepository.findAll(spec, pageable);

        return productsPage.map(productMapper::toDTO);
    }

    Page<Product> getProductsWithPaginationFilteringAndSorting(int page, int size, String search, String sort, boolean asc, String category, String subcategory) {
        Sort sortOrder = sort ? (asc ? Sort.by(sort).ascending() : Sort.by(sort).descending()) : Sort.unsorted()
        PageRequest pageRequest = PageRequest.of(page, size, sortOrder)
        Specification<Product> spec = ProductSpecification.getSpecification(search, category, subcategory);
        Page<Product> productPage = productRepository.findAll(spec, pageRequest);
        return productPage;
    }

    Product getProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id)
        if (optionalProduct.isEmpty()) {
            logger.info("Product not found with id: $id")
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: $id")
        } else {
            return optionalProduct.get()
        }
    }

    Product patchProduct(Long id, Product product) {
        Optional<Product> optionalExistingProduct = productRepository.findById(id);
        if (optionalExistingProduct.isEmpty()) {
            logger.info("Product not found with id: $id")
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found with id: $id")
        } else {
            logger.info("Product found with id: $id")
        }
    }

    List<CategoryDTO> getProductCategories(){
        List<Category> categories = categoryRepository.findAll()

        return categories.collect { category ->
            def subcategoryDTOs = category.subcategories.collect { subcategory ->
                def sizeDTOs = subcategory.sizes.collect { size ->
                    new SizeDTO(size.id, size.quantity, size.unit.name, size.unit.abbreviation)
                }

                def ingredientDTOs = subcategory.defaultIngredients.collect { i ->
                    new IngredientDTO(
                            i.ingredient.inventoryId,
                            i.ingredient.name,
                            i.ingredient.quantity,
                            i.ingredient.size.unit.abbreviation
                    )
                }

                new SubcategoryDTO(subcategory.id, subcategory.name, sizeDTOs, ingredientDTOs)
            }

            new CategoryDTO(category.id, category.name, subcategoryDTOs)
        }

    }

}
