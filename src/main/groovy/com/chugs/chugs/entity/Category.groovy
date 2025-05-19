package com.chugs.chugs.entity

import jakarta.persistence.*

@Entity
@Table(name = "category")
class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" )
    Long id;

    @Column(name = "name", nullable = false, unique = true)
    String name;

    @Column(name = "description")
    String description;

    @OneToMany(mappedBy = "category", orphanRemoval = true)
    List<Subcategory> subcategories = []

}
