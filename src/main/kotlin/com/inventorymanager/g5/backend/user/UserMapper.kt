package com.inventorymanager.g5.backend.user

import com.inventorymanager.g5.backend.user.dto.UserCreateDto
import com.inventorymanager.g5.backend.user.dto.UserDto
import com.inventorymanager.g5.backend.user.model.User
import java.util.*

class UserMapper {
    fun createDtoToUser(userCreateDto: UserCreateDto): User =
        User(userCreateDto.login, userCreateDto.password , userCreateDto.firstname, userCreateDto.lastname, userCreateDto.description)

    fun userToUserDto(user: User): UserDto = UserDto(user.id!!, user.firstname, user.lastname)

    fun usersToUsersDto(users: Iterable<User>): List<UserDto> = users.map { UserDto(it.id!!, it.firstname, it.lastname) }
}