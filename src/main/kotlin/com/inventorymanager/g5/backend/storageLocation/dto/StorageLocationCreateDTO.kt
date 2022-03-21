package com.inventorymanager.g5.backend.storageLocation.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.inventorymanager.g5.backend.tag.TagDTO

class StorageLocationCreateDTO(
        @JsonProperty("name")
        var name: String? = null,

        @JsonProperty("storageParentId")
        var storageParentId: String? = null,

        @JsonProperty("tags")
        var tags: LinkedHashSet<TagDTO>? = null,

        @JsonProperty("usersIds")
        var userId: LinkedHashSet<String>? = null
)