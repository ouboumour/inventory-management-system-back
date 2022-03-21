package com.inventorymanager.g5.backend.objects

import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import com.inventorymanager.g5.backend.objects.dto.ObjectCreateDto
import com.inventorymanager.g5.backend.objects.dto.ObjectDto
import com.inventorymanager.g5.backend.storageLocation.StorageLocation
import com.inventorymanager.g5.backend.storageLocation.StorageLocationRepository
import com.inventorymanager.g5.backend.tag.Tag
import com.inventorymanager.g5.backend.tag.TagDTO
import com.inventorymanager.g5.backend.tag.TagMapper
import com.inventorymanager.g5.backend.tag.TagRepository
import com.inventorymanager.g5.backend.objects.model.ObjectModel
import com.inventorymanager.g5.backend.user.UserRepository
import com.inventorymanager.g5.backend.user.model.User
import org.mapstruct.*
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring", uses = [TagMapper::class])
abstract class ObjectMapper {
    @Autowired
    private lateinit var storageLocationRepository: StorageLocationRepository

    @Autowired
    private lateinit var tagRepository: TagRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Mappings(
            Mapping(target = "id", ignore = true),
            Mapping(source = "storageLocationId", target = "storageLocation", qualifiedByName = ["idToStorageLocation"]),
            Mapping(source = "tags", target = "tags", qualifiedByName = ["mapTags"]),
            Mapping(source = "userId", target = "user", qualifiedByName = ["idToUser"])
    )
    @Throws(ResourceDoesNotExistException::class)
    abstract fun objectCreateDtoToObject(objectDto: ObjectCreateDto): ObjectModel

    @Mappings(
            Mapping(source = "storageLocation.id", target = "storageLocationId"),
            Mapping(source = "user", target = "userId", qualifiedByName = ["userToId"])
    )
    abstract fun objectToObjectDto(objet: ObjectModel): ObjectDto

    @Mappings(
            Mapping(source = "storageLocation.id", target = "storageLocationId"),
            Mapping(source = "user", target = "userId", qualifiedByName = ["userToId"])
    )
    abstract fun objectsToObjectsDto(objects: Iterable<ObjectModel>): List<ObjectDto>

    @Mappings(
            Mapping(target = "id", ignore = true),
            Mapping(target = "qrCode", ignore = true),
            Mapping(target = "name", source = "name"),
            Mapping(source="storageLocationId", target = "storageLocation", qualifiedByName = ["idToStorageLocation"]),
            Mapping(source="tags", target = "tags", qualifiedByName = ["mapTags"]),
            Mapping(source = "userId", target = "user", qualifiedByName = ["idToUser"])
    )
    @Throws(ResourceDoesNotExistException::class)
    abstract fun mergeToDomain(objectDto : ObjectDto, @MappingTarget objectModel : ObjectModel)

    @Throws(ResourceDoesNotExistException::class)
    @Named("idToStorageLocation")
    fun idToStorageLocation(storageLocationId: String?): StorageLocation? {
        if (storageLocationId == null) return null
        return storageLocationRepository
                .findById(storageLocationId)
                .orElseThrow { ResourceDoesNotExistException(StorageLocation::class.java, "id", storageLocationId) }
    }

    @Throws(ResourceDoesNotExistException::class)
    @Named("idToUser")
    fun idToUser(userId: String?): User? {
        if (userId == null) return null
        return userRepository
                .findById(userId)
                .orElseThrow { ResourceDoesNotExistException(User::class.java, "id", userId) }
    }

    @Named("userToId")
    fun userToId(user: Set<User>?): LinkedHashSet<String>? {
        if (user == null) return null
        var userIds = mutableSetOf(String())
        userIds.removeIf{ it.isEmpty() }
        user.forEach{ it.id?.let { it1 -> userIds.add(it1) } }

        return userIds as LinkedHashSet<String>
    }

    @Named("mapTags")
    fun mapTags(tagDTO: TagDTO): Tag? {
        return tagRepository
                .findByName(tagDTO.name)
                .orElseThrow {
                    ResourceDoesNotExistException(Tag::class.java, "name", tagDTO.name)
                }
    }
}