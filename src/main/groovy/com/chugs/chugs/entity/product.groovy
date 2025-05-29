package com.chugs.chugs.entity


import jakarta.persistence.*

@Entity
@Table(name = "product")
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id

    @Column(nullable = false, length = 100)
    String name

    @Column(nullable = false, length = 500)
    String description

    @Column(nullable = false)
    BigDecimal price

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    Category category

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id", nullable = false)
    Subcategory subcategory

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", nullable = false)
    Size size

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    List<ProductDefaultIngredient> defaultIngredients
}
