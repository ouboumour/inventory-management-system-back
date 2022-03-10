package com.inventorymanager.g5.backend.storageLocation

import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import org.mapstruct.*
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring")
abstract class StorageLocationMapper{

        @Autowired
        private lateinit var storageLocationRepository: StorageLocationRepository

        @Mapping(source="storageParent.id", target = "storageParentId")
        abstract fun domainToDto(storageLocation : StorageLocation?) : StorageLocationDTO

        @Named("withoutChildren")
        @Mappings(
                Mapping(source="storageParent.id", target = "storageParentId"),
                Mapping(target = "storageChildren", ignore = true)
        )
        abstract fun domainToDtoWithoutChildren(storageLocation : StorageLocation?) : StorageLocationDTO

        @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "storageChildren", ignore = true),
        Mapping(source="storageParentId", target = "storageParent", qualifiedByName = ["mapParent"])
        )
        abstract fun dtoToDomain(storageLocationDto: StorageLocationDTO) : StorageLocation

        @Mappings(
                Mapping(target = "id", ignore = true),
                Mapping(target = "storageChildren", ignore = true),
                Mapping(source="storageParentId", target = "storageParent", qualifiedByName = ["mapParent"])
        )abstract fun mergeToDomain(storageLocationDto : StorageLocationDTO, @MappingTarget storageLocation : StorageLocation) : Unit

        @Throws(ResourceDoesNotExistException::class)
        @Named("mapParent")
        fun mapParent(storageParentId: String?) : StorageLocation? {
                if(storageParentId == null) return null
                return storageLocationRepository
                        .findById(storageParentId)
                        .orElseThrow { ResourceDoesNotExistException(StorageLocation::class.java, storageParentId)}
        }

        @AfterMapping
        fun mapDomainToDto(@MappingTarget  storageLocationDto: StorageLocationDTO,  entity : StorageLocation) : Unit{
                storageLocationDto.level = countParent(entity)
        }

        private fun countParent(entity : StorageLocation) : Int{
                if(entity.storageParent == null) return 0
                return 1 + countParent(entity.storageParent!!)
        }
}