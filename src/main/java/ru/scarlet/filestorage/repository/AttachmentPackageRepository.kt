package ru.scarlet.filestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository
import ru.scarlet.filestorage.entity.AttachmentPackage
import java.util.*

interface AttachmentPackageRepository : JpaRepository<AttachmentPackage, UUID> {


    fun findByLink(link: String): List<AttachmentPackage>
}