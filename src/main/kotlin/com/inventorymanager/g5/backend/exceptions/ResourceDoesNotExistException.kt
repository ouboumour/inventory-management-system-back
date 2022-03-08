package com.inventorymanager.g5.backend.exceptions

class ResourceDoesNotExistException(modelClass: Class<*>, id: String?) :
    Exception(modelClass.name + " with id '" + id + "' does not exist.")