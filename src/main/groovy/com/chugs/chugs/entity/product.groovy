package com.chugs.chugs.entity
import com.chugs.chugs.converter.StringListConverter;
import jakarta.persistence.*
import org.hibernate.annotations.ManyToAny
import com.chugs.chugs.entity.Category


@Entity
@Table(name = "product")
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long productId

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
    @JoinColumn(name = "fk_size_id", nullable = false)
    Size size

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductDefaultIngredient> defaultIngredients = []
}
