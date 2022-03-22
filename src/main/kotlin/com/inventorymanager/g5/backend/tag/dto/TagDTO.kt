package com.inventorymanager.g5.backend.tag.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class TagDTO(
        @JsonProperty("id")
        var id: String? = null,

        @JsonProperty("name")
        var name: String? = null
)