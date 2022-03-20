package com.inventorymanager.g5.backend.storageLocation

import com.inventorymanager.g5.backend.exceptions.DuplicateEntityException
import com.inventorymanager.g5.backend.exceptions.QrCodeException
import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/api/storage-locations")
@Validated
class StorageLocationController @Autowired constructor(val storageLocationService: StorageLocationService) {

    @GetMapping
    fun getAll(@RequestParam(defaultValue = "true") onlyRoots : Boolean): ResponseEntity<Iterable<StorageLocationDTO>> {
        return ResponseEntity.ok(storageLocationService.getAll(onlyRoots));
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ResponseEntity<StorageLocationDTO> {
        try {
            return ResponseEntity.ok(storageLocationService.get(id))
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

    @GetMapping("/{id}/storage-direct-children")
    fun getDirectStorageChildren(@PathVariable id: String) : ResponseEntity<Iterable<StorageLocationDTO>> {
        try {
            return ResponseEntity.ok(storageLocationService.getStorageDirectChildren(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @GetMapping("/{id}/objects")
    fun getStorageObjects(@PathVariable id: String) : String {
        return "This call logic isn't implemented yet!!"
    }

    @GetMapping("/{id}/absolute-name-path")
    fun getStorageLocationNameWithAbsolutePath(@PathVariable id: String) : ResponseEntity<LinkedList<String?>> {
        try {
            return ResponseEntity.ok(storageLocationService.getStorageLocationNameWithAbsolutePath(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @PostMapping("/{id}/generate-qr-code")
    fun generateQrCode(@PathVariable id: String) : ResponseEntity<String>{
        try {
            return ResponseEntity.ok(storageLocationService.generateQrCode(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        } catch (e: QrCodeException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }
}