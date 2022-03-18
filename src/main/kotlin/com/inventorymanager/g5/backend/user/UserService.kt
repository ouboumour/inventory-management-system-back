package com.inventorymanager.g5.backend.user

import com.inventorymanager.g5.backend.exceptions.DuplicateEntityException
import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import com.inventorymanager.g5.backend.user.dto.UserCreateDto
import com.inventorymanager.g5.backend.user.dto.UserDto
import com.inventorymanager.g5.backend.user.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.validation.Valid

@Service
class UserService @Autowired constructor(
    val repository: UserRepository,
    val mapper: UserMapper
) {

    @Throws(DuplicateEntityException::class)
    fun addUser(@Valid userCreateDto: UserCreateDto): UserDto {
        // check if login is not a duplicate
        if (repository.findByLogin(userCreateDto.login) != null) { throw DuplicateEntityException(User::class.java, "login", userCreateDto.login) }

        return mapper.userToUserDto(repository.save(mapper.createDtoToUser(userCreateDto)))
    }

    fun findAllUsers(): List<UserDto> = mapper.usersToUsersDto(repository.findAll())

    fun findUserById(id: String): UserDto? = repository.findById(id).get().let { mapper.userToUserDto(it) }

    fun findUserByLogin(login: String): UserDto? = repository.findByLogin(login)?.let { mapper.userToUserDto(it) }

    @Throws(ResourceDoesNotExistException::class)
    fun deleteUserById(id: String) {
        if (this.repository.findById(id).isEmpty) {
            throw ResourceDoesNotExistException(User::class.java , id)
        }
        return repository.deleteById(id)
    }

    @Throws(ResourceDoesNotExistException::class)
    fun updateUserById(id: String, update: UserCreateDto): UserDto {

        if (this.repository.findById(id).isEmpty) {
            throw ResourceDoesNotExistException(User::class.java, id)
        }
        return mapper.userToUserDto(repository.save(User(id = id, firstname = update.firstname, lastname = update.lastname, login = update.login, password = update.password)))
    }
}