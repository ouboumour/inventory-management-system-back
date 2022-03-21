package com.inventorymanager.g5.backend.user

import com.inventorymanager.g5.backend.exceptions.DuplicateEntityException
import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import com.inventorymanager.g5.backend.user.dto.UserCreateDto
import com.inventorymanager.g5.backend.user.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("api/users")
class UserController @Autowired constructor(val service: UserService) {

    @GetMapping("")
    fun getAllUsers(): ResponseEntity<Iterable<UserDto>> {
        return ResponseEntity.ok(service.findAllUsers());
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): ResponseEntity<UserDto> {
        try {
            return ResponseEntity.ok(service.findUserById(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @GetMapping("/user/{login}")
    fun getUserByLogin(@PathVariable login: String): ResponseEntity<UserDto> {
        try {
            return ResponseEntity.ok(service.findUserByLogin(login))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @PostMapping("/user/create")
    fun createUser( @RequestBody userCreateDto: UserCreateDto): ResponseEntity<UserDto?>? {
        try {
            return ResponseEntity<UserDto?>(service.addUser(userCreateDto), HttpStatus.OK)
        } catch (e: DuplicateEntityException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable id: String): ResponseEntity<Void> {
        try {
            service.deleteUserById(id)
            return ResponseEntity.noContent().build()
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @PutMapping("/{id}")
    fun updateUserById(@PathVariable id: String, @RequestBody userCreateDto: UserCreateDto): ResponseEntity<UserDto> {
        try {
            return ResponseEntity.ok(service.updateUserById(id, userCreateDto))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }
}