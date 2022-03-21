package com.inventorymanager.g5.backend.objects

import com.inventorymanager.g5.backend.exceptions.DuplicateEntityException
import com.inventorymanager.g5.backend.exceptions.QrCodeException
import com.inventorymanager.g5.backend.exceptions.ResourceDoesNotExistException
import com.inventorymanager.g5.backend.objects.dto.ObjectCreateDto
import com.inventorymanager.g5.backend.objects.dto.ObjectDto
import com.inventorymanager.g5.backend.objects.model.ObjectModel
import com.inventorymanager.g5.backend.storageLocation.*
import com.inventorymanager.g5.backend.user.UserRepository
import com.inventorymanager.g5.backend.user.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.validation.Valid

@Service
class ObjectService @Autowired constructor(
        private val storageLocationRepository: StorageLocationRepository,
        private val objectRepository: ObjectRepository,
        private val userRepository: UserRepository,
        private val objectMapper: ObjectMapper
){
    @Throws(ResourceDoesNotExistException::class, DuplicateEntityException::class)
    fun create(@Valid objectCreateDto: ObjectCreateDto) : ObjectDto {
        if(objectCreateDto.storageLocationId != null) {
            // check parent storageLocation existence
            storageLocationRepository
                    .findById(objectCreateDto.storageLocationId!!)
                    .orElseThrow{ ResourceDoesNotExistException(StorageLocation::class.java, "id", objectCreateDto.storageLocationId) }
        }

        return objectMapper.objectToObjectDto(objectRepository.save(objectMapper.objectCreateDtoToObject(objectCreateDto)))
    }

    @Throws(ResourceDoesNotExistException::class, QrCodeException::class)
    fun generateQrCode(objectId: String) : String? {
        val objectModel: ObjectModel = objectRepository
                .findById(objectId)
                .orElseThrow { ResourceDoesNotExistException(ObjectModel::class.java, "id", objectId) }

        if (objectModel.qrCode != null) {
            throw QrCodeException(ObjectModel::class.java, "id", objectId)
        }

        objectModel.qrCode = UUID.randomUUID().toString()
        objectRepository.save(objectModel)
        return objectModel.qrCode
    }

    fun getAll(): Iterable<ObjectDto>? {
        return objectMapper.objectsToObjectsDto(objectRepository.findAll())
    }

    fun get(id: String): ObjectDto? {
        val objectModel: ObjectModel? = objectRepository.findById(id).orElseThrow { ResourceDoesNotExistException(ObjectModel::class.java, "id", id) }
        return objectModel?.let { objectMapper.objectToObjectDto(it) }
    }

    fun update(id: String, objectDto: ObjectDto): ObjectDto? {
        val objectModel : ObjectModel = objectRepository.findById(id).orElseThrow{ResourceDoesNotExistException(ObjectModel::class.java , "id", id)}

        objectMapper.mergeToDomain(objectDto, objectModel)
        return objectMapper.objectToObjectDto(objectRepository.save(objectModel))
    }

    fun delete(id: String): String? {
        val objectModel : ObjectModel = objectRepository.findById(id).orElseThrow{ResourceDoesNotExistException(ObjectModel::class.java , "id", id)}

        objectRepository.deleteById(id)
        return id
    }

    fun getObjectsOfUser(id: String): Iterable<ObjectDto>? {
        val user: User = userRepository.findById(id).orElseThrow{ResourceDoesNotExistException(User::class.java , "id", id)}

        val objectModels: Iterable<ObjectModel> = objectRepository.findAllByUserId(user.id)
        return objectMapper.objectsToObjectsDto(objectModels)
    }

    fun getObjectsOfStorage(id: String): Iterable<ObjectDto>? {
        val storageLocation: StorageLocation = storageLocationRepository.findById(id).orElseThrow{ResourceDoesNotExistException(StorageLocation::class.java , "id", id)}

        val objectModels: Iterable<ObjectModel> = objectRepository.findAllByUserId(storageLocation.id)
        return objectMapper.objectsToObjectsDto(objectModels)
    }
}