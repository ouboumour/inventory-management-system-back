package com.inventorymanager.g5.backend.tag

import com.inventorymanager.g5.backend.user.model.User
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
class Tag(
        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        @Column(unique = true, nullable = false, updatable = false)
        var id: String?,

        @Column(nullable = false)
        var name: String?,

        @ManyToOne(
                targetEntity = User::class,
                fetch = FetchType.LAZY)
        var user: User?
)