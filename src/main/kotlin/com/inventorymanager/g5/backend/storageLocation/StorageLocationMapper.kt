package com.inventorymanager.g5.backend.storageLocation

import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import com.inventorymanager.g5.backend.tag.Tag
import com.inventorymanager.g5.backend.tag.TagDTO
import com.inventorymanager.g5.backend.tag.TagMapper
import com.inventorymanager.g5.backend.tag.TagRepository
import org.mapstruct.*
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring", uses = [TagMapper::class])
abstract class StorageLocationMapper{

        @Autowired
        private lateinit var storageLocationRepository: StorageLocationRepository

        @Autowired
        private lateinit var tagRepository: TagRepository

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
        Mapping(source="storageParentId", target = "storageParent", qualifiedByName = ["idToStorageLocation"]),
        Mapping(source="tags", target = "tags", qualifiedByName = ["mapTags"])
        )
        @Throws(ResourceDoesNotExistException::class)
        abstract fun dtoToDomain(storageLocationDto: StorageLocationDTO) : StorageLocation

        @Mappings(
                Mapping(target = "id", ignore = true),
                Mapping(target = "storageChildren", ignore = true),
                Mapping(source="storageParentId", target = "storageParent", qualifiedByName = ["idToStorageLocation"]),
                Mapping(source="tags", target = "tags", qualifiedByName = ["mapTags"])
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
                        .orElseThrow { ResourceDoesNotExistException(StorageLocation::class.java, "id", storageLocationId)}
        }

        @Named("storageLocationToId")
        fun toId(obj: StorageLocation) : String? =  obj.id

        @Named("mapTags")
        fun mapTags(tagDTO: TagDTO) : Tag? {
                return tagRepository
                        .findByName(tagDTO.name)
                        .orElseThrow { ResourceDoesNotExistException(Tag::class.java, "name", tagDTO.name)
                        }
        }
}