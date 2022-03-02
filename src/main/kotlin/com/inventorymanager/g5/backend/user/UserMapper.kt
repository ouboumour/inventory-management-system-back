package com.inventorymanager.g5.backend.user

import com.inventorymanager.g5.backend.user.dto.UserCreateDto
import com.inventorymanager.g5.backend.user.dto.UserDto
import com.inventorymanager.g5.backend.user.model.User

class UserMapper {
    fun createDtoToUser(userCreateDto: UserCreateDto): User =
        User(
            userCreateDto.login,
            userCreateDto.password,
            userCreateDto.firstname,
            userCreateDto.lastname,
        )

    fun userToUserDto(user: User): UserDto = UserDto(user.id!!, user.login, user.firstname, user.lastname)

    fun usersToUsersDto(users: Iterable<User>): List<UserDto> =
        users.map { UserDto(it.id!!, it.login, it.firstname, it.lastname) }

    fun updateUser(it: User, userCreateDto: UserCreateDto): User = User(
        userCreateDto.login,
        userCreateDto.password,
        userCreateDto.firstname,
        userCreateDto.lastname,
        it.id
    )

}