package com.inventorymanager.g5.backend.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UserCreateDto(
        @NotNull(message = "Login can not be null")
        @NotBlank(message = "Login can not be blank")
        @JsonProperty("login")
        val login: String,

        @JsonProperty("password")
        val password: String,

        @JsonProperty("firstname")
        val firstname: String,

        @JsonProperty("lastname")
        val lastname: String
    )