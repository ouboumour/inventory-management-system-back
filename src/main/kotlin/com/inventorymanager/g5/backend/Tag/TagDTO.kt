package com.inventorymanager.g5.backend.Tag

import javax.validation.constraints.NotNull

class TagDTO(
    val id: Int,

    @NotNull
    val name: String
)