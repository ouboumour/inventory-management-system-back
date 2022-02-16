package com.inventorymanager.g5.backend.user.dto

data class UserCreateDto(
    val login: String,
    val password: String,
    val firstname: String,
    val lastname: String,
)