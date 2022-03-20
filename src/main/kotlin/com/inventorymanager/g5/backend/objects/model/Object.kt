package com.inventorymanager.g5.backend.user.model

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class User(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(unique = true, nullable = false, updatable = false)
    var id: String?,

    val name: String,

    @ManyToOne(
        targetEntity = StorageLocation::class,
        fetch = FetchType.EAGER
    )
    var storageLocation: StorageLocation?,

    @ManyToMany(
        targetEntity = Tag::class,
        fetch = FetchType.LAZY,
    )
    @OrderBy("name ASC")
    var tags: Set<Tag>? = LinkedHashSet(),
)
