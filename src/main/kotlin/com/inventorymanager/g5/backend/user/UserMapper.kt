package com.inventorymanager.g5.backend.user

import com.inventorymanager.g5.backend.user.dto.UserCreateDto
import com.inventorymanager.g5.backend.user.dto.UserDto
import com.inventorymanager.g5.backend.user.model.User
import org.mapstruct.*

@Mapper(componentModel = "spring")
abstract class UserMapper {
    @Mapping(target = "id", ignore = true)
    abstract fun createDtoToUser(userCreateDto: UserCreateDto): User

    @Mapping(target = "id", source = "user.id")
    abstract fun userToUserDto(user: User): UserDto

    @Mapping(target = "id", source = "user.id")
    abstract fun usersToUsersDto(users: Iterable<User>): List<UserDto>
}