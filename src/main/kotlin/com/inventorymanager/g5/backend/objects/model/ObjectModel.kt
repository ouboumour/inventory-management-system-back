package com.inventorymanager.g5.backend.objects.model

import com.inventorymanager.g5.backend.storageLocation.StorageLocation
import com.inventorymanager.g5.backend.tag.Tag
import com.inventorymanager.g5.backend.user.model.User
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
data class ObjectModel(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(unique = true, nullable = false, updatable = false)
    var id: String?,

    @Column(name = "name", nullable = false)
    var name: String?,

    @Column(unique = true)
    var qrCode: String?,

    @ManyToOne(
        targetEntity = StorageLocation::class,
        fetch = FetchType.EAGER
    )
    var storageLocation: StorageLocation?,

    @ManyToMany(
            targetEntity = User::class,
            fetch = FetchType.LAZY,
    )
    var user: Set<User>? = LinkedHashSet(),

    @ManyToMany(
        targetEntity = Tag::class,
        fetch = FetchType.LAZY)
    @OrderBy("name ASC")
    var tags: Set<Tag>? = LinkedHashSet()
)
