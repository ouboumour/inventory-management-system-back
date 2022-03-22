package com.inventorymanager.g5.backend.tag

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TagRepository : CrudRepository<Tag, String> {
    fun findAllByOrderByName(): Iterable<Tag>
    fun findByName(name: String?): Optional<Tag>
    fun findAllByUserId(user_id: String?): Iterable<Tag>
    fun findByNameAndUserId(name: String, user_id: String): Optional<Tag>
}