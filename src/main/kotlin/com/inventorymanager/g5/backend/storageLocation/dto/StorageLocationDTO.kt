package com.inventorymanager.g5.backend.storageLocation.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.inventorymanager.g5.backend.tag.dto.TagDTO

data class StorageLocationDTO(
        @JsonProperty("id")
        var id: String? = null,

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