package com.inventorymanager.g5.backend.storageLocation

import com.inventorymanager.g5.backend.exceptions.DuplicateEntityException
import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.validation.Valid

@Service
class StorageLocationService @Autowired constructor(
    private val storageLocationRepository: StorageLocationRepository,
    private val storageLocationMapper: StorageLocationMapper
){

    fun getAll(onlyRoots : Boolean = true) : Iterable<StorageLocationDTO> {
       val storageLocations : Iterable<StorageLocation> =
         if(onlyRoots) storageLocationRepository.getOnlyRoots()
         else storageLocationRepository.findAll()
       return storageLocations.map(storageLocationMapper::domainToDto)
    }

    @Throws(ResourceDoesNotExistException::class)
    fun get(storageLocationId : String) : StorageLocationDTO {
        val storageLocation: StorageLocation = storageLocationRepository
            .findById(storageLocationId)
            .orElseThrow { ResourceDoesNotExistException(StorageLocation::class.java, "id", storageLocationId) }
        return storageLocationMapper.domainToDto(storageLocation)
    }

    @Throws(ResourceDoesNotExistException::class, DuplicateEntityException::class)
    fun create(@Valid storageLocationDTO: StorageLocationDTO) : StorageLocationDTO {
        val entity : Optional<StorageLocation> = storageLocationRepository.findByName(storageLocationDTO.name)
        // check if name is not a duplicate
        if (entity.isPresent) {
            throw DuplicateEntityException(StorageLocation::class.java, "name", storageLocationDTO.name)
        }

        if(storageLocationDTO.storageParentId != null) {
            // check parent storageLocation existence
            storageLocationRepository
                .findById(storageLocationDTO.storageParentId!!)
                .orElseThrow{ ResourceDoesNotExistException(StorageLocation::class.java, "id", storageLocationDTO.storageParentId)}
        }

        val stockLocation : StorageLocation = storageLocationMapper.dtoToDomain(storageLocationDTO)
        return storageLocationMapper.domainToDto(storageLocationRepository.save(stockLocation))
    }

    @Throws(ResourceDoesNotExistException::class, DuplicateEntityException::class)
    fun update(storageLocationId: String, @Valid storageLocationDTO: StorageLocationDTO) : StorageLocationDTO? {

        val storageLocationById : StorageLocation =
            storageLocationRepository.findById(storageLocationId).orElseThrow{ResourceDoesNotExistException(StorageLocation::class.java , "id", storageLocationDTO.id)}

        val storageLocationByName : Optional<StorageLocation> = storageLocationRepository.findByName(storageLocationDTO.name)
        // check if name is not a duplicate
        if (storageLocationByName.isPresent && storageLocationByName.get().id != storageLocationId) {
            throw DuplicateEntityException(StorageLocation::class.java, "name", storageLocationDTO.name)
        }

        if(storageLocationDTO.storageParentId != null) {
            // check parent storageLocation existence
            storageLocationRepository
                .findById(storageLocationDTO.storageParentId!!)
                .orElseThrow{ ResourceDoesNotExistException(StorageLocation::class.java, "id", storageLocationDTO.storageParentId)}
        }

        storageLocationMapper.mergeToDomain(storageLocationDTO, storageLocationById)
        // prevent displaying the updated storage children in response body
        return storageLocationMapper.domainToDtoWithoutChildren(storageLocationRepository.save(storageLocationById))
    }

    @Throws(ResourceDoesNotExistException::class)
    fun delete (storageLocationId: String) : String {
        if (this.storageLocationRepository.findById(storageLocationId).isEmpty) {
            throw ResourceDoesNotExistException(StorageLocation::class.java , "id", storageLocationId)
        }
        storageLocationRepository.deleteById(storageLocationId)
        return storageLocationId
    }

    @Throws(ResourceDoesNotExistException::class)
    fun getStorageDirectChildren(storageLocationId : String): Iterable<StorageLocationDTO> {
        storageLocationRepository
            .findById(storageLocationId)
            .orElseThrow { ResourceDoesNotExistException(StorageLocation::class.java, "id", storageLocationId) }

        return storageLocationRepository.getStorageDirectChildren(storageLocationId)
            .map(storageLocationMapper::domainToDto)
            .sortedBy { p -> p.name }
    }

    @Throws(ResourceDoesNotExistException::class)
    fun getStorageLocationNameWithAbsolutePath(storageLocationId : String): LinkedList<String?> {
        val storageLocation : StorageLocation = storageLocationRepository
            .findById(storageLocationId)
            .orElseThrow { ResourceDoesNotExistException(StorageLocation::class.java, "id", storageLocationId) }

        var pathNamesList =  LinkedList<String?>(listOf(storageLocation.name))
        addName(storageLocation.storageParent?.id, pathNamesList)

        return pathNamesList
    }

    private fun addName(storageLocationId: String?, pathNamesList :LinkedList<String?>) {
        if (storageLocationId != null) {
            val parentStorageLocation: StorageLocation = storageLocationRepository.findById(storageLocationId).get()
            pathNamesList.add(0, parentStorageLocation.name)
            addName(parentStorageLocation.storageParent?.id, pathNamesList)
        }
    }
}