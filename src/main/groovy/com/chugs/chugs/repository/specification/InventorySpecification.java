package com.chugs.chugs.repository.specification;

import com.chugs.chugs.entity.Inventory;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class InventorySpecification {

    /**
     * Creates a JPA Specification for filtering Inventory entities based on search criteria.
     * The specification generates a dynamic query that performs:
     * 1. A partial text match (LIKE) across multiple fields (name, description, category, subcategory)
     *    when a search term is provided
     * 2. Exact matches for category and subcategory when specified
     * Creates a specification for filtering Inventory entities based on the provided criteria.
     *
     * @param search Optional search term that will be matched against name, description,
     *              category, and subcategory fields using LIKE operator
     * @param category Optional category value for exact matching
     * @param subCategory Optional subcategory value for exact matching
     * @return A Specification that combines all the search criteria using AND operator,
     *         returns all records if no criteria specified
     */
    public static Specification<Inventory> getSpecification(String search, String category, String subCategory)
    {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (search != null && !search.isEmpty()) {
                predicates.add (
                        criteriaBuilder.or(
                                criteriaBuilder.like(root.get("name"), "%" + search + "%"),
                                criteriaBuilder.like(root.get("description"), "%" + search + "%"),
                                criteriaBuilder.like(root.get("inventoryCategory"), "%" + search + "%"),
                                criteriaBuilder.like(root.get("subcategory"), "%" + search + "%")
                        )
                );
            }
            if (category != null && !category.isEmpty()) {
                predicates.add( criteriaBuilder.equal(root.get("inventoryCategory"), category) );
            }
            if (subCategory != null && !subCategory.isEmpty()) {
                predicates.add(
                        criteriaBuilder.equal(root.get("subcategory"), subCategory)
                );
            }
            return criteriaBuilder.and( predicates.toArray(Predicate[]::new) );
        };
    }
}