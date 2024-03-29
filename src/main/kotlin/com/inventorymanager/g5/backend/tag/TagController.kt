package com.inventorymanager.g5.backend.tag

import com.inventorymanager.g5.backend.exceptions.DuplicateEntityException
import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import com.inventorymanager.g5.backend.tag.dto.TagCreateDto
import com.inventorymanager.g5.backend.tag.dto.TagDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid

@RestController
@RequestMapping("/api/tags")
@Validated
class TagController @Autowired constructor(val tagService: TagService) {
    @GetMapping
    fun getAll(): ResponseEntity<Iterable<TagDTO>> {
      return ResponseEntity.ok(tagService.getAll());
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ResponseEntity<TagDTO> {
        try {
            return ResponseEntity.ok(tagService.get(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @GetMapping("/user/{id}")
    fun getTagsByUserId(@PathVariable id: String): ResponseEntity<Iterable<TagDTO>> {
        try {
            return ResponseEntity.ok(tagService.getAllByUserId(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @GetMapping("/user/{id}/tag/{name}")
    fun getTagByNameAndByUserId(@PathVariable id: String, @PathVariable name: String): ResponseEntity<TagDTO> {
        try {
            return ResponseEntity.ok(tagService.getTagByNameAndByUserId(name ,id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @PostMapping
    fun create(@Valid @RequestBody tagCreateDto: TagCreateDto) : ResponseEntity<TagDTO>{
        try {
            return ResponseEntity<TagDTO>(tagService.create(tagCreateDto), HttpStatus.OK);
        } catch (e : DuplicateEntityException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id : String , @Valid @RequestBody tagCreateDto: TagCreateDto) : ResponseEntity<TagDTO>{
        try {
            return ResponseEntity.ok(tagService.update(id, tagCreateDto));
        } catch (e : ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e);
        } catch (e : DuplicateEntityException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e);
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) : ResponseEntity<String>{
        try {
            return ResponseEntity.ok(tagService.delete(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

}