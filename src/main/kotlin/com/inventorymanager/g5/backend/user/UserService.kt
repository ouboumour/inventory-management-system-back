package com.inventorymanager.g5.backend.user

import com.inventorymanager.g5.backend.user.dto.UserCreateDto
import com.inventorymanager.g5.backend.user.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    val repository: UserRepository
) {
    val mapper: UserMapper = UserMapper()

    fun addUser(userCreateDto: UserCreateDto): UserDto =
        mapper.userToUserDto(repository.save(mapper.createDtoToUser(userCreateDto)))

    fun findAllUsers(): List<UserDto> = mapper.usersToUsersDto(repository.findAll())

    fun findUserById(id: String): UserDto? = repository.findById(id).get().let { mapper.userToUserDto(it) }

    fun findUserByLogin(login: String): UserDto? = repository.findByLogin(login)?.let { mapper.userToUserDto(it) }

    fun deleteUserById(id: String) = repository.deleteById(id)

    fun updateUserById(id: String, userCreateDto: UserCreateDto) =
        repository.findById(id).get()?.let { repository.save(mapper.updateUser(it, userCreateDto)) }


}