package com.inventorymanager.g5.backend.objects.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.inventorymanager.g5.backend.tag.dto.TagDTO

data class ObjectDto(
        @JsonProperty("id")
    var id: String? = null,

        @JsonProperty("name")
    var name: String? = null,

        @JsonProperty("qrCode")
    var qrCode: String? = null,

        @JsonProperty("storageLocationId")
    var storageLocationId: String? = null,

        @JsonProperty("tags")
    var tags: LinkedHashSet<TagDTO>? = null,

        @JsonProperty("usersIds")
    var userId: LinkedHashSet<String>? = null
)