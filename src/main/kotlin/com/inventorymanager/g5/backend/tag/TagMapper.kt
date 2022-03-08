package com.inventorymanager.g5.backend.tag

import org.mapstruct.*

@Mapper(componentModel = "spring")
interface TagMapper{

    fun domainToDto(tag : Tag) : TagDTO

    @Mapping(target = "id", ignore = true)
    fun dtoToDomain(tagDto: TagDTO) : Tag

    @Mapping(target = "id", ignore = true)
    fun mergeToDomain(tagDto : TagDTO, @MappingTarget tag : Tag) : Unit
}