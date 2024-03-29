package com.inventorymanager.g5.backend.storageLocation

import com.inventorymanager.g5.backend.exceptions.DuplicateEntityException
import com.inventorymanager.g5.backend.exceptions.QrCodeException
import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import com.inventorymanager.g5.backend.objects.ObjectService
import com.inventorymanager.g5.backend.storageLocation.dto.StorageLocationCreateDTO
import com.inventorymanager.g5.backend.storageLocation.dto.StorageLocationDTO
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
class StorageLocationController @Autowired constructor(val storageLocationService: StorageLocationService, val objectService: ObjectService) {

    @GetMapping
    fun getAll(@RequestParam(defaultValue = "true") onlyRoots: Boolean): ResponseEntity<Iterable<StorageLocationDTO>> {
        return ResponseEntity.ok(storageLocationService.getAll(onlyRoots));
    }

    @GetMapping("/user/{id}")
    fun getAllByUserId(@PathVariable id: String): ResponseEntity<Iterable<StorageLocationDTO>> {
        try {
            return ResponseEntity.ok(storageLocationService.getStorageOfUser(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @GetMapping("/user/{id}/roots")
    fun getAllRootsByUserId(@PathVariable id: String): ResponseEntity<Iterable<StorageLocationDTO>> {
        try {
            return ResponseEntity.ok(storageLocationService.getRootStorageOfUser(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ResponseEntity<StorageLocationDTO> {
        try {
            return ResponseEntity.ok(storageLocationService.get(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @GetMapping("/qrcode/{name}")
    fun getByQrCode(@PathVariable name: String): ResponseEntity<StorageLocationDTO> {
        try {
            return ResponseEntity.ok(storageLocationService.getByQrCode(name))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @PostMapping
    fun create(@RequestBody stockLocationDTO: StorageLocationCreateDTO): ResponseEntity<StorageLocationDTO?>? {
        return try {
            ResponseEntity<StorageLocationDTO?>(storageLocationService.create(stockLocationDTO), HttpStatus.OK)
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        } catch (e: DuplicateEntityException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody stockLocationDTO: StorageLocationCreateDTO): ResponseEntity<StorageLocationDTO> {
        try {
            return ResponseEntity.ok(storageLocationService.update(id, stockLocationDTO));
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e);
        } catch (e: DuplicateEntityException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e);
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<String> {
        try {
            return ResponseEntity.ok(storageLocationService.delete(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @GetMapping("/{id}/storage-direct-children")
    fun getDirectStorageChildren(@PathVariable id: String): ResponseEntity<Iterable<StorageLocationDTO>> {
        try {
            return ResponseEntity.ok(storageLocationService.getStorageDirectChildren(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @GetMapping("/{id}/absolute-name-path")
    fun getStorageLocationNameWithAbsolutePath(@PathVariable id: String): ResponseEntity<LinkedList<String?>> {
        try {
            return ResponseEntity.ok(storageLocationService.getStorageLocationNameWithAbsolutePath(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        }
    }

    @PostMapping("/{id}/generate-qr-code")
    fun generateQrCode(@PathVariable id: String): ResponseEntity<String> {
        try {
            return ResponseEntity.ok(storageLocationService.generateQrCode(id))
        } catch (e: ResourceDoesNotExistException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message, e)
        } catch (e: QrCodeException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message, e)
        }
    }
}