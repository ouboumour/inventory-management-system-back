package com.inventorymanager.g5.backend.user.model

import com.inventorymanager.g5.backend.objects.model.ObjectModel
import com.inventorymanager.g5.backend.storageLocation.StorageLocation
import com.inventorymanager.g5.backend.tag.Tag
import net.bytebuddy.dynamic.scaffold.MethodGraph
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*
import kotlin.collections.LinkedHashSet

@Entity
data class User(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(unique = true, nullable = false, updatable = false)
    var id: String?,

    @Column(unique = true)
    var login: String,

    val password: String,

    var firstname: String,

    var lastname: String,

    @ManyToMany(
            targetEntity = ObjectModel::class,
            fetch = FetchType.LAZY,
    )
    var objects: Set<ObjectModel>? = LinkedHashSet(),

    @OneToMany(
            targetEntity = Tag::class,
            fetch = FetchType.LAZY,
    )
    var tags: Set<Tag>? = LinkedHashSet(),

    @ManyToMany(
    targetEntity = StorageLocation::class,
    fetch = FetchType.LAZY,
    )
    var storages: Set<StorageLocation>? = LinkedHashSet()
)
