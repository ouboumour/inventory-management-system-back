package com.inventorymanager.g5.backend.storageLocation

import com.inventorymanager.g5.backend.exceptions.DuplicateEntityException
import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class StorageLocationService @Autowired constructor(
    private val storageLocationRepository: StorageLocationRepository,
    private val storageLocationMapper: StorageLocationMapper
){

    fun getAll() : Iterable<StorageLocationDTO> {
       val storageLocations : Iterable<StorageLocation> = storageLocationRepository.getAllRoot()
       return storageLocations.map(storageLocationMapper::domainToDto)
    }

    @Throws(ResourceDoesNotExistException::class)
    fun get(storageLocationId : String, withoutChildren : Boolean) : StorageLocationDTO {
        val storageLocation: StorageLocation = storageLocationRepository
            .findById(storageLocationId)
            .orElseThrow { ResourceDoesNotExistException(StorageLocation::class.java, storageLocationId) }

        if (withoutChildren) return storageLocationMapper.domainToDtoWithoutChildren(storageLocation)
        return storageLocationMapper.domainToDto(storageLocation)
    }

    //DuplicateEntityException, ResourceDoesNotExistException, InvalidLevelDepthException, EntitiesLoopFoundException
    @Throws(ResourceDoesNotExistException::class, DuplicateEntityException::class)
    fun create(storageLocationDTO: StorageLocationDTO) : StorageLocationDTO {
        val entity : Optional<StorageLocation> = storageLocationRepository.findByName(storageLocationDTO.name)
        // check if name is not a duplicate
        if (entity.isPresent) {
            throw DuplicateEntityException(StorageLocation::class.java, "name", storageLocationDTO.name)
        }

        if(storageLocationDTO.storageParentId != null) {
            // check parent storageLocation existence
            val parentEntity : StorageLocation =storageLocationRepository
                .findById(storageLocationDTO.storageParentId!!)
                .orElseThrow{ ResourceDoesNotExistException(StorageLocation::class.java, storageLocationDTO.storageParentId)}
        }

        val stockLocation : StorageLocation = storageLocationMapper.dtoToDomain(storageLocationDTO)
        return storageLocationMapper.domainToDto(storageLocationRepository.save(stockLocation))
    }
}