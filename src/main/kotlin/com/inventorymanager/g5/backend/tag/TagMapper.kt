package com.inventorymanager.g5.backend.tag

import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import com.inventorymanager.g5.backend.tag.dto.TagCreateDto
import com.inventorymanager.g5.backend.tag.dto.TagDTO
import com.inventorymanager.g5.backend.user.UserRepository
import com.inventorymanager.g5.backend.user.model.User
import org.mapstruct.*
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring")
abstract class TagMapper{
    @Autowired
    private lateinit var userRepository: UserRepository

    abstract fun domainToDto(tag : Tag) : TagDTO

    @Mappings(
            Mapping(target = "id", ignore = true),
            Mapping(source = "userId", target = "user", qualifiedByName = ["idToUser"])
    )
    abstract fun dtoToDomain(tagDto: TagCreateDto) : Tag

    @Mappings(
            Mapping(target = "id", ignore = true),
            Mapping(source = "userId", target = "user", qualifiedByName = ["idToUser"])
    )
    abstract fun mergeToDomain(tagDto : TagCreateDto, @MappingTarget tag : Tag)

    @Throws(ResourceDoesNotExistException::class)
    @Named("idToUser")
    fun idToUser(userId: String?): User? {
        if (userId == null) return null
        return userRepository
                .findById(userId)
                .orElseThrow { ResourceDoesNotExistException(User::class.java, "id", userId) }
    }
}