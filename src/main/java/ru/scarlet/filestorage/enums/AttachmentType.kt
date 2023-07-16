package ru.scarlet.filestorage.enums

import lombok.Getter

@Getter
enum class AttachmentType(val code: Int, val attachmentType: String) {
    DOCUMENT(0, "DOCUMENT"),
    PERSONAL(1, "PERSONAL"),
    PUBLIC(2, "PUBLIC");

    private fun getByCode(code: Int): AttachmentType? {
        return when (code) {
            DOCUMENT.code -> DOCUMENT
            PERSONAL.code -> PERSONAL
            PUBLIC.code -> PUBLIC
            else -> null
        }
    }
}
