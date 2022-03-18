package com.inventorymanager.g5.backend.exceptions

class DuplicateEntityException(modelClass: Class<*>, attribute: String?, value: String?) :
    Exception(modelClass.name + " with " + attribute + " '" + value + "' already exists.")