package com.inventorymanager.g5.backend.storageLocation.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.inventorymanager.g5.backend.tag.dto.TagDTO
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class StorageLocationDTO(
        @JsonProperty("id")
        var id: String? = null,

        @NotNull(message = "Name can not be null")
        @NotBlank(message = "Name can not be blank")
        @JsonProperty("name")
        var name: String? = null,

        @JsonProperty("qrCode")
        var qrCode: String? = null,

        @JsonProperty("level")
        var level: Int? = null,

        @JsonProperty("storageParentId")
        var storageParentId: String? = null,

        @JsonProperty("storageChildrenIds")
        var storageChildrenIds: LinkedHashSet<String>? = null,

        @JsonProperty("tags")
        var tags: LinkedHashSet<TagDTO>? = null,

        @JsonProperty("usersIds")
        var userId: LinkedHashSet<String>? = null
)