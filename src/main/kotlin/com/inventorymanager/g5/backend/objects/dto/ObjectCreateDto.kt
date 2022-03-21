package com.inventorymanager.g5.backend.objects.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.inventorymanager.g5.backend.tag.TagDTO
import com.inventorymanager.g5.backend.user.dto.UserDto
import com.inventorymanager.g5.backend.user.model.User

class ObjectCreateDto (
            @JsonProperty("name")
            var name: String? = null,

            @JsonProperty("storageLocationId")
            var storageLocationId: String? = null,

            @JsonProperty("tags")
            var tags: LinkedHashSet<TagDTO>? = null,

            @JsonProperty("usersIds")
            var userId: LinkedHashSet<String>? = null
)
