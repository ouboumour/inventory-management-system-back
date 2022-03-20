package com.inventorymanager.g5.backend.user

import com.inventorymanager.g5.backend.user.dto.UserCreateDto
import com.inventorymanager.g5.backend.user.dto.UserDto
import com.inventorymanager.g5.backend.user.model.User
import org.mapstruct.*

@Mapper(componentModel = "spring", uses = [TagMapper::class])
abstract class UserMapper {
        @Autowired
        private lateinit var storageLocationRepository: StorageLocationRepository

        @Autowired
        private lateinit var tagRepository: TagRepository


        @Mappings(
                Mapping(target = "id", ignore = true),
                Mapping(source="storageLocationId", target = "storageLocation", qualifiedByName = ["idToStorageLocation"]),
                Mapping(source="tags", target = "tags", qualifiedByName = ["mapTags"])
                )
        @Throws(ResourceDoesNotExistException::class)
        abstract fun objectDtoToObject(objectDto: ObjectDto): Object

        @Mappings(
                Mapping(source="storageLocation.id", target = "storageLocationId"),
                )
        abstract fun objectToObjectDto(object: Object): ObjectDto

        @Mapping(target = "id", source = "object.id")
        abstract fun objectsToObjectsDto(objects: Iterable<Object>): List<ObjectDto>

    @Throws(ResourceDoesNotExistException::class)
    @Named("idToStorageLocation")
    fun toObject(storageLocationId: String?) : StorageLocation? {
            if(storageLocationId == null) return null
            return storageLocationRepository
                    .findById(storageLocationId)
                    .orElseThrow { ResourceDoesNotExistException(StorageLocation::class.java, "id", storageLocationId)}
    }

    @Named("mapTags")
    fun mapTags(tagDTO: TagDTO) : Tag? {
            return tagRepository
                    .findByName(tagDTO.name)
                    .orElseThrow { ResourceDoesNotExistException(Tag::class.java, "name", tagDTO.name)
                    }
    }
}