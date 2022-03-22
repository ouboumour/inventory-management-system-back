package com.inventorymanager.g5.backend.tag.dto

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class TagDTO(
        @NotNull(message = "Name can not be null")
        @NotBlank(message = "Name can not be blank")
        @JsonProperty("name")
        var name: String? = null
)