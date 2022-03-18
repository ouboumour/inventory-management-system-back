package com.inventorymanager.g5.backend.storageLocation

import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import org.mapstruct.*
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring")
abstract class StorageLocationMapper{

        @Autowired
        private lateinit var storageLocationRepository: StorageLocationRepository

        @Mappings(
                Mapping(source="storageParent.id", target = "storageParentId"),
                Mapping(source="storageChildren", target = "storageChildrenIds", qualifiedByName = ["storageLocationToId"]),
                Mapping(target = "level", ignore = true)
                )
        abstract fun domainToDto(storageLocation : StorageLocation?) : StorageLocationDTO

        @Named("withoutChildren")
        @Mappings(
                Mapping(source="storageParent.id", target = "storageParentId"),
                Mapping(target = "storageChildrenIds", ignore = true),
                Mapping(target = "level", ignore = true)
                )
        abstract fun domainToDtoWithoutChildren(storageLocation : StorageLocation?) : StorageLocationDTO

        @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "storageChildren", ignore = true),
        Mapping(source="storageParentId", target = "storageParent", qualifiedByName = ["idToStorageLocation"])
        )
        @Throws(ResourceDoesNotExistException::class)
        abstract fun dtoToDomain(storageLocationDto: StorageLocationDTO) : StorageLocation

        @Mappings(
                Mapping(target = "id", ignore = true),
                Mapping(target = "storageChildren", ignore = true),
                Mapping(source="storageParentId", target = "storageParent", qualifiedByName = ["idToStorageLocation"])
        )
        @Throws(ResourceDoesNotExistException::class)
        abstract fun mergeToDomain(storageLocationDto : StorageLocationDTO, @MappingTarget storageLocation : StorageLocation) : Unit

        @AfterMapping
        fun mapDomainToDto(@MappingTarget  storageLocationDto: StorageLocationDTO,  entity : StorageLocation) : Unit{
                storageLocationDto.level = countParent(entity)
        }

        private fun countParent(entity : StorageLocation) : Int{
                if(entity.storageParent == null) return 0
                return 1 + countParent(entity.storageParent!!)
        }

        @Throws(ResourceDoesNotExistException::class)
        @Named("idToStorageLocation")
        fun toObject(storageLocationId: String?) : StorageLocation? {
                if(storageLocationId == null) return null
                return storageLocationRepository
                        .findById(storageLocationId)
                        .orElseThrow { ResourceDoesNotExistException(StorageLocation::class.java, storageLocationId)}
        }

        @Named("storageLocationToId")
        fun toId(obj: StorageLocation) : String? =  obj.id
}