package com.inventorymanager.g5.backend.user.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserCreateDto(
        @JsonProperty("login")
        val login: String,

        @JsonProperty("password")
        val password: String,

        @JsonProperty("firstname")
        val firstname: String,

        @JsonProperty("lastname")
        val lastname: String
    )