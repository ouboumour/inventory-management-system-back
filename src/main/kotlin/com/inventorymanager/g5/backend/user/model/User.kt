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

    @Column(unique = true)
    var login: String,

    val password: String,

    var firstname: String,

    var lastname: String,
)
