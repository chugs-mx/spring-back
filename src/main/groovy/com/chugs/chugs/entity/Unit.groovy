package com.chugs.chugs.entity

import jakarta.persistence.*

@Entity
@Table(name = "unit")
class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Column(nullable = false, unique = true, length = 50)
    String name

    @Column(nullable = false, unique = true, length = 50)
    String abbreviation

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Size> sizes = []
}
