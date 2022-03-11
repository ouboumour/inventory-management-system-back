package com.inventorymanager.g5.backend.tag

import com.inventorymanager.g5.backend.exceptions.DuplicateEntityException
import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.validation.Valid

@Service
class TagService @Autowired constructor(
    private val tagRepository: TagRepository,
    private val tagMapper: TagMapper,
){
    fun getAll() : Iterable<TagDTO> =tagRepository.findAll().map(tagMapper::domainToDto)

    @Throws(ResourceDoesNotExistException::class)
    fun get(tagId : String) : TagDTO {
        return tagRepository
            .findById(tagId)
            .map(tagMapper::domainToDto)
            .orElseThrow{ResourceDoesNotExistException(Tag::class.java , tagId)}
    }

    @Throws(DuplicateEntityException::class)
    fun create(@Valid tagDTO: TagDTO) : TagDTO{
        if (tagRepository.findByName(tagDTO.name).isPresent) {
            throw DuplicateEntityException(Tag::class.java, "name", tagDTO.name)
        }
        val tag : Tag = tagMapper.dtoToDomain(tagDTO);
        return tagMapper.domainToDto(tagRepository.save(tag))
    }

    @Throws(DuplicateEntityException::class)
    fun update(tagId: String, @Valid tagDTO: TagDTO) : TagDTO? {

        val tagById : Tag = tagRepository.findById(tagId).orElseThrow{ResourceDoesNotExistException(Tag::class.java , tagDTO.id)}
        val tagByName : Optional<Tag> = tagRepository.findByName(tagDTO.name)

        // Case when another Tag exists with the same name
        if (tagByName.isPresent && tagByName.get().id != tagId) {
            throw DuplicateEntityException(Tag::class.java, "name", tagDTO.name)
        }

        tagMapper.mergeToDomain(tagDTO, tagById)
        return tagMapper.domainToDto(tagRepository.save(tagById))
    }

    @Throws(ResourceDoesNotExistException::class)
    fun delete (tagId : String) : String {
        if (this.tagRepository.findById(tagId).isEmpty) {
            throw ResourceDoesNotExistException(Tag::class.java , tagId)
        }
        tagRepository.deleteById(tagId)
        return tagId
    }

}