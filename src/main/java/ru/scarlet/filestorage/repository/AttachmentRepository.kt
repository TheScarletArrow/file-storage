package ru.scarlet.filestorage.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.scarlet.filestorage.entity.Attachment
import java.util.*

@Repository
interface AttachmentRepository : JpaRepository<Attachment?, UUID?> {
    fun findByAuthorOrderByCreatedDesc(author: String?): List<Attachment?>?
}