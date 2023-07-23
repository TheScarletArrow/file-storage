package ru.scarlet.filestorage.dto

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor
data class ResponseData (
    val filename: String? = null,
    val downloadURL: String? = null,
    val fileType: String? = null,
    val size: Long? = null,

)
