package com.inventorymanager.g5.backend.storageLocation

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StorageLocationRepository : CrudRepository<StorageLocation, String> {
    fun findByName(name: String?): Optional<StorageLocation>

    @Query("select sl FROM StorageLocation sl WHERE sl.storageParent.id is null")
    fun getOnlyRoots() : List<StorageLocation>
}