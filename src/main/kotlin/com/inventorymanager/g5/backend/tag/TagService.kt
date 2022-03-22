package com.inventorymanager.g5.backend.tag

import com.inventorymanager.g5.backend.exceptions.DuplicateEntityException
import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import com.inventorymanager.g5.backend.tag.dto.TagCreateDto
import com.inventorymanager.g5.backend.tag.dto.TagDTO
import com.inventorymanager.g5.backend.user.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.validation.Valid

@Service
class TagService @Autowired constructor(
    private val tagRepository: TagRepository,
    private val tagMapper: TagMapper,
){
    fun getAll() : Iterable<TagDTO> =tagRepository.findAllByOrderByName().map(tagMapper::domainToDto)

    @Throws(ResourceDoesNotExistException::class)
    fun get(tagId : String) : TagDTO {
        return tagRepository
            .findById(tagId)
            .map(tagMapper::domainToDto)
            .orElseThrow{ResourceDoesNotExistException(Tag::class.java , "id", tagId)}
    }

    @Throws(ResourceDoesNotExistException::class)
    fun getAllByUserId(userId : String) : Iterable<TagDTO> {
        return tagRepository
                .findAllByUserId(userId)
                .map(tagMapper::domainToDto)
    }

    @Throws(ResourceDoesNotExistException::class)
    fun getTagByNameAndByUserId(tagName: String, userId : String) : TagDTO {
        return tagRepository
                .findByNameAndUserId(tagName, userId)
                .map(tagMapper::domainToDto)
                .orElseThrow{ResourceDoesNotExistException(User::class.java , "id", userId)}
    }

    @Throws(DuplicateEntityException::class)
    fun create(@Valid tagDTO: TagCreateDto) : TagDTO {
        if (tagRepository.findByName(tagDTO.name).isPresent) {
            throw DuplicateEntityException(Tag::class.java, "name", tagDTO.name)
        }
        val tag : Tag = tagMapper.dtoToDomain(tagDTO);
        return tagMapper.domainToDto(tagRepository.save(tag))
    }

    @Throws(DuplicateEntityException::class)
    fun update(tagId: String, @Valid tagCreateDto: TagCreateDto) : TagDTO? {

        val tagById : Tag = tagRepository.findById(tagId).orElseThrow{ResourceDoesNotExistException(Tag::class.java , "id", tagId)}
        val tagByName : Optional<Tag> = tagRepository.findByName(tagCreateDto.name)

        // Case when another Tag exists with the same name
        if (tagByName.isPresent && tagByName.get().id != tagId) {
            throw DuplicateEntityException(Tag::class.java, "name", tagCreateDto.name)
        }

        tagMapper.mergeToDomain(tagCreateDto, tagById)
        return tagMapper.domainToDto(tagRepository.save(tagById))
    }

    @Throws(ResourceDoesNotExistException::class)
    fun delete (tagId : String) : String {
        if (this.tagRepository.findById(tagId).isEmpty) {
            throw ResourceDoesNotExistException(Tag::class.java , "id", tagId)
        }
        tagRepository.deleteById(tagId)
        return tagId
    }

}