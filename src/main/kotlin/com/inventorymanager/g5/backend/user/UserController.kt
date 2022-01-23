package com.inventorymanager.g5.backend.user

import com.inventorymanager.g5.backend.user.dto.UserCreateDto
import com.inventorymanager.g5.backend.user.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/users")
class UserController @Autowired constructor(val service: UserService) {

    @GetMapping("")
    fun getAllUsers(): List<UserDto> = service.findAllUsers()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): ResponseEntity<UserDto> {
        val userDto = service.findUserById(id)
        return if (userDto != null) ResponseEntity<UserDto>(userDto, HttpStatus.FOUND)
        else ResponseEntity<UserDto>(null, HttpStatus.NOT_FOUND)
    }

    @GetMapping("/user/{login}")
    fun getUserByLogin(@PathVariable login: String): ResponseEntity<UserDto> {
        val userDto = service.findUserByLogin(login)
        return if (userDto != null) ResponseEntity<UserDto>(userDto, HttpStatus.FOUND)
        else ResponseEntity<UserDto>(null, HttpStatus.NOT_FOUND)
    }

    @PostMapping("/user/create")
    fun createUser(@RequestBody userCreateDto: UserCreateDto): UserDto = service.addUser(userCreateDto)

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable id: String): HttpStatus {
        service.deleteUserById(id)
        return HttpStatus.OK
    }

    @PutMapping("/{id}")
    fun updateUserById(@PathVariable id: String, @RequestBody userCreateDto: UserCreateDto): HttpStatus {
        service.updateUserById(id, userCreateDto)
        return HttpStatus.OK
    }

}