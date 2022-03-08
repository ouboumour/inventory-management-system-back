package com.inventorymanager.g5.backend.tag

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class TagDTO(

    var id: String? = null,

    @field:NotNull
    @field:NotBlank
    var name: String? = null
)