package com.inventorymanager.g5.backend.user.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User(
    @Column(unique = true) var login: String,
    val password: String,
    var firstname: String,
    var lastname: String,
    var description: String? = null,
    @Id var id: String? = UUID.randomUUID().toString()
)
