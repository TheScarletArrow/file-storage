package ru.scarlet.filestorage.dto

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDateTime
import java.util.*

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
data class AllAttachments (
    private val attachmentUUID: UUID? = null,
    private val filename: String? = null,
    private val fileType: String? = null,
    private val created: LocalDateTime? = null,
    private val attachmentTypeCode: Int? = null,
    private val author: String? = null,
    private val isInfiniteDownloads: Boolean? = null,
    private val downloadsLeft: Int? = null
)
