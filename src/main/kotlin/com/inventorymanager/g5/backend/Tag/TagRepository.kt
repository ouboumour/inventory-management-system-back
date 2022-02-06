package com.inventorymanager.g5.backend.Tag

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : CrudRepository<Tag, Long> {
    fun findByName(name: String): Tag
}