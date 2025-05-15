package com.chugs.chugs.entity
import com.chugs.chugs.converter.StringListConverter;
import jakarta.persistence.*


@Entity
@Table(name = "product")
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productId

    @Column(nullable = false, length = 100)
    String name

    @Column(nullable = false, length = 500)
    String description

    @Column(nullable = false)
    BigDecimal price

    @Column(nullable = false)
    String size

    @Convert(converter = StringListConverter.class)
    @Column(nullable = false, columnDefinition = "TEXT")
    List<String> types

    @Convert(converter = StringListConverter.class)
    @Column(nullable = false, columnDefinition = "TEXT")
    List<String> defaultIngredients

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    Category productCategory

    enum Category{
        HAMBURGERS, DRINKS, EXTRA, POTATOES, DESSERTS
    }
}
