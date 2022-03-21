package com.inventorymanager.g5.backend.objects

import com.inventorymanager.g5.backend.exceptions.DuplicateEntityException
import com.inventorymanager.g5.backend.exceptions.QrCodeException
import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import com.inventorymanager.g5.backend.objects.dto.ObjectCreateDto
import com.inventorymanager.g5.backend.objects.dto.ObjectDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid

@RestController
@RequestMapping("api/objects")
class ObjectController @Autowired constructor(val objectService: ObjectService) {
    @GetMapping
    fun getAll(): ResponseEntity<Iterable<ObjectDto>> {
        return ResponseEntity.ok(objectService.getAll());
    }

    @GetMapping("/user/{id}")
    fun getAllByUserId(@PathVariable id: String): ResponseEntity<Iterable<ObjectDto>> {
        try {
            return ResponseEntity.ok(objectService.getObjectsOfUser(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @GetMapping("/storage/{id}")
    fun getAllByStorageId(@PathVariable id: String) : ResponseEntity<Iterable<ObjectDto>> {
        try {
            return ResponseEntity.ok(objectService.getObjectsOfStorage(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ResponseEntity<ObjectDto> {
        try {
            return ResponseEntity.ok(objectService.get(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @Valid @RequestBody objectDto: ObjectDto): ResponseEntity<ObjectDto> {
        try {
            return ResponseEntity.ok(objectService.update(id, objectDto));
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e);
        } catch (e: DuplicateEntityException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e);
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<String> {
        try {
            return ResponseEntity.ok(objectService.delete(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @PostMapping
    fun create(@RequestBody objectCreateDto: ObjectCreateDto): ResponseEntity<ObjectDto?>? {
        return try {
            ResponseEntity<ObjectDto?>(objectService.create(objectCreateDto), HttpStatus.OK)
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        } catch (e: DuplicateEntityException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }

    @PostMapping("/{id}/generate-qr-code")
    fun generateQrCode(@PathVariable id: String): ResponseEntity<String> {
        try {
            return ResponseEntity.ok(objectService.generateQrCode(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        } catch (e: QrCodeException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }
}