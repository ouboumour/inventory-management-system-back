package com.inventorymanager.g5.backend.storageLocation

import com.inventorymanager.g5.backend.objects.model.ObjectModel
import com.inventorymanager.g5.backend.tag.Tag
import com.inventorymanager.g5.backend.user.model.User
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
class StorageLocation(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        @Column(unique = true, nullable = false, updatable = false)
        var id: String?,

        @Column(unique = true, nullable = false)
        var name: String?,

        @Column(unique = true)
        var qrCode: String?,

        @ManyToOne(
                targetEntity = StorageLocation::class,
                fetch = FetchType.LAZY
        )
        var storageParent: StorageLocation?,

        @OneToMany(
                targetEntity = StorageLocation::class,
                fetch = FetchType.LAZY,
                cascade = [CascadeType.ALL],
                mappedBy = "storageParent",
                orphanRemoval = true)
        @OrderBy("name ASC")
        var storageChildren: Set<StorageLocation>? = LinkedHashSet(),


        @ManyToMany(
                targetEntity = Tag::class,
                fetch = FetchType.LAZY,
        )
        @OrderBy("name ASC")
        var tags: Set<Tag>? = LinkedHashSet(),

        @OneToMany(
                targetEntity = ObjectModel::class,
                fetch = FetchType.LAZY)
        var objects: Set<ObjectModel>? = LinkedHashSet(),

        @ManyToMany(
                targetEntity = User::class,
                fetch = FetchType.LAZY)
        var user: Set<User>? = java.util.LinkedHashSet()
)