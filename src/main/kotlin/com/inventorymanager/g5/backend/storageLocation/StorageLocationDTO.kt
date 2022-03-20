package com.inventorymanager.g5.backend.storageLocation

import com.fasterxml.jackson.annotation.JsonProperty
import com.inventorymanager.g5.backend.tag.TagDTO
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class StorageLocationDTO(
    @field:JsonProperty(access = JsonProperty.Access.READ_ONLY)
    var id: String? = null,

    @field:NotNull
    @field:NotBlank
    var name: String? = null,

    @field:JsonProperty(access = JsonProperty.Access.READ_ONLY)
    var qrCode: String? = null,

    @field:JsonProperty(access = JsonProperty.Access.READ_ONLY)
    var level : Int? = null,

    var storageParentId: String? = null,

    @field:JsonProperty(access = JsonProperty.Access.READ_ONLY)
    var storageChildrenIds: LinkedHashSet<String>? = null,

    @field:Valid
    var tags: LinkedHashSet<TagDTO>? = null
)