package com.inventorymanager.g5.backend.tag

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TagRepository : CrudRepository<Tag, String> {
    fun findByName(name: String?): Optional<Tag>
}