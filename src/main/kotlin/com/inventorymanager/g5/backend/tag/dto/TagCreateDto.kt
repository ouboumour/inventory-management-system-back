package com.inventorymanager.g5.backend.tag.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TagCreateDto(
        @JsonProperty("name")
        var name: String? = null,

        @JsonProperty("userId")
        var userId: String?
)