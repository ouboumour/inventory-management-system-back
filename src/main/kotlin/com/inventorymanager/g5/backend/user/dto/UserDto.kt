package com.inventorymanager.g5.backend.user.dto

import com.fasterxml.jackson.annotation.JsonProperty

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
