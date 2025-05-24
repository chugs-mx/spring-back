package com.chugs.chugs.entity

import jakarta.persistence.*

@Entity
@Table(name = "subcategory")
class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description")
    String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    Category category

    @OneToMany(mappedBy = "subcategory")
    List<Size> sizes;

    @OneToMany(mappedBy = "subcategory")
    List<SubcategoryDefaultIngredient> defaultIngredients;
}
