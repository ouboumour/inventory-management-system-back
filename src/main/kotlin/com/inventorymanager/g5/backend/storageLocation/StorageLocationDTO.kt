package com.inventorymanager.g5.backend.storageLocation

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class StorageLocationDTO(
    var id: String? = null,

    @field:NotNull
    @field:NotBlank
    var name: String? = null,

    @NotNull
    var level : Int? = null,

    var storageParentId: String? = null,

    var storageChildren: LinkedHashSet<StorageLocationDTO>? = null
)