package com.inventorymanager.g5.backend.storageLocation

import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import com.inventorymanager.g5.backend.storageLocation.dto.StorageLocationCreateDTO
import com.inventorymanager.g5.backend.storageLocation.dto.StorageLocationDTO
import com.inventorymanager.g5.backend.tag.Tag
import com.inventorymanager.g5.backend.tag.TagDTO
import com.inventorymanager.g5.backend.tag.TagMapper
import com.inventorymanager.g5.backend.tag.TagRepository
import com.inventorymanager.g5.backend.user.UserRepository
import com.inventorymanager.g5.backend.user.model.User
import org.mapstruct.*
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring", uses = [TagMapper::class])
abstract class StorageLocationMapper{

        @Autowired
        private lateinit var storageLocationRepository: StorageLocationRepository

        @Autowired
        private lateinit var tagRepository: TagRepository

        @Autowired
        private lateinit var userRepository: UserRepository

        @Mappings(
                Mapping(source="storageParent.id", target = "storageParentId"),
                Mapping(source="storageChildren", target = "storageChildrenIds", qualifiedByName = ["storageLocationToId"]),
                Mapping(target = "level", ignore = true),
                Mapping(source = "user", target = "userId", qualifiedByName = ["userToId"])
                )
        abstract fun domainToDto(storageLocation : StorageLocation?) : StorageLocationDTO

        @Named("withoutChildren")
        @Mappings(
                Mapping(source="storageParent.id", target = "storageParentId"),
                Mapping(target = "storageChildrenIds", ignore = true),
                Mapping(target = "level", ignore = true),
                Mapping(source = "user", target = "userId", qualifiedByName = ["userToId"])
                )
        abstract fun domainToDtoWithoutChildren(storageLocation : StorageLocation?) : StorageLocationDTO

        @Mappings(
                Mapping(target = "id", ignore = true),
                Mapping(target = "storageChildren", ignore = true),
                Mapping(source="storageParentId", target = "storageParent", qualifiedByName = ["idToStorageLocation"]),
                Mapping(source="tags", target = "tags", qualifiedByName = ["mapTags"]),
                Mapping(source = "userId", target = "user", qualifiedByName = ["idToUser"])
        )
        @Throws(ResourceDoesNotExistException::class)
        abstract fun dtoToDomain(storageLocationDto: StorageLocationCreateDTO) : StorageLocation

        @Mappings(
                Mapping(target = "id", ignore = true),
                Mapping(target = "storageChildren", ignore = true),
                Mapping(source="storageParentId", target = "storageParent", qualifiedByName = ["idToStorageLocation"]),
                Mapping(source="tags", target = "tags", qualifiedByName = ["mapTags"]),
                Mapping(source = "userId", target = "user", qualifiedByName = ["idToUser"])
        )
        @Throws(ResourceDoesNotExistException::class)
        abstract fun mergeToDomain(storageLocationDto : StorageLocationCreateDTO, @MappingTarget storageLocation : StorageLocation) : Unit

        @AfterMapping
        fun mapDomainToDto(@MappingTarget  storageLocationDto: StorageLocationDTO, entity : StorageLocation) : Unit{
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
}