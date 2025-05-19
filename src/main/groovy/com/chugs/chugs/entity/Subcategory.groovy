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
    @JoinColumn(name = "fk_category_id", nullable = false)
    Category category
}
