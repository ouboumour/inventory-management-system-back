package com.inventorymanager.g5.backend.exceptions

class QrCodeException(modelClass: Class<*>, attribute: String?, value: String?) :
Exception(modelClass.name + " with " + attribute + " '" + value + "' has already associated QR code.")