package com.inventorymanager.g5.backend.storageLocation

import com.inventorymanager.g5.backend.exceptions.DuplicateEntityException
import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.validation.Valid


@RestController
@RequestMapping("/api/storage-locations")
@Validated
class StorageLocationController @Autowired constructor(val storageLocationService: StorageLocationService) {
    @GetMapping
    fun getAll(): ResponseEntity<Iterable<StorageLocationDTO>> {
        return ResponseEntity.ok(storageLocationService.getAll());
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String, @RequestParam(defaultValue = "true") withoutChildren : Boolean): ResponseEntity<StorageLocationDTO> {
        try {
            return ResponseEntity.ok(storageLocationService.get(id, withoutChildren))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @PostMapping
    fun create(@Valid @RequestBody stockLocationDTO: StorageLocationDTO): ResponseEntity<StorageLocationDTO?>? {
        return try {
            ResponseEntity<StorageLocationDTO?>(storageLocationService.create(stockLocationDTO), HttpStatus.OK)
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        } catch (e: DuplicateEntityException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id : String , @Valid @RequestBody stockLocationDTO: StorageLocationDTO) : ResponseEntity<StorageLocationDTO>{
        try {
            return ResponseEntity.ok(storageLocationService.update(id, stockLocationDTO));
        } catch (e : ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e);
        } catch (e : DuplicateEntityException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e);
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) : ResponseEntity<String>{
        try {
            return ResponseEntity.ok(storageLocationService.delete(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @GetMapping("/{id}/objects")
    fun getStorageObjects(@PathVariable id: String) : String {
        return "This call logic isn't implemented yet!!"
    }
}