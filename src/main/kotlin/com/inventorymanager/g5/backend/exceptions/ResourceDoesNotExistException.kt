package com.inventorymanager.g5.backend.exceptions

class ResourceDoesNotExistException(modelClass: Class<*>, attribute: String?, value: String?) :
    Exception(modelClass.name + " with " + attribute + " '" + value + "' does not exist.")