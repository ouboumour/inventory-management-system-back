package com.inventorymanager.g5.backend.Tag

import javax.persistence.*

@Entity
class Tag(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val name: String
)