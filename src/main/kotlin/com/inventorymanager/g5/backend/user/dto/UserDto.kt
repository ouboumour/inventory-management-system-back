package com.inventorymanager.g5.backend.user.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class UserDto(
        @JsonProperty("id")
        val id: String,

        @JsonProperty("login")
        val login: String,

        @JsonProperty("firstname")
        val firstname: String,

        @JsonProperty("lastname")
        val lastname: String
    )
