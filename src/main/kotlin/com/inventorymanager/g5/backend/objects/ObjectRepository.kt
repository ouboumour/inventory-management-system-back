package com.inventorymanager.g5.backend.objects

import com.inventorymanager.g5.backend.objects.model.ObjectModel
import com.inventorymanager.g5.backend.storageLocation.StorageLocation
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ObjectRepository : CrudRepository<ObjectModel, String> {
    fun findAllByUserId(userId: String?): Iterable<ObjectModel>
    fun findAllByStorageLocationId(storageLocationId: String?): Iterable<ObjectModel>
}