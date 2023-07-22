package ru.scarlet.filestorage.dto

import java.util.*


data class AttachmentUploadResponse
    (
    var packageUUID: UUID?=null,
    var list: List<ResponseData>? = null
)
