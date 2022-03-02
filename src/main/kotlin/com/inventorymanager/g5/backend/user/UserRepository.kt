package com.inventorymanager.g5.backend.user

import com.inventorymanager.g5.backend.user.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, String> {
    fun findByLogin(login: String): User?
}