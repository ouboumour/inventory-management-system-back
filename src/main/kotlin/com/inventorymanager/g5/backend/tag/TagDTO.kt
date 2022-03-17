package com.inventorymanager.g5.backend.tag

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class TagDTO(

    @field:JsonProperty(access = JsonProperty.Access.READ_ONLY)
    var id: String? = null,

    @field:NotNull
    @field:NotBlank
    var name: String? = null
)