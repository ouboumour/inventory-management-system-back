package com.inventorymanager.g5.backend.storageLocation

import com.inventorymanager.g5.backend.objects.model.ObjectModel
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StorageLocationRepository : CrudRepository<StorageLocation, String> {
    fun findByName(name: String?): Optional<StorageLocation>

    @Query("select sl FROM StorageLocation sl WHERE sl.storageParent.id is null")
    fun getOnlyRoots() : List<StorageLocation>

//    @Query("select sl FROM StorageLocation sl WHERE sl.qrCode = :name")
    fun findByQrCode(name: String?): Optional<StorageLocation>

    @Query("select sl FROM StorageLocation sl WHERE sl.storageParent.id = :storageId")
    fun getStorageDirectChildren(storageId : String?) : List<StorageLocation>

    fun findAllByUserId(userId: String?): Iterable<StorageLocation>

    fun findAllByUserIdAndStorageParentIsNull(userId: String?): Iterable<StorageLocation>
}