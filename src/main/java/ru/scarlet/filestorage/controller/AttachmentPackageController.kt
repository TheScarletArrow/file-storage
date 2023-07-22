package ru.scarlet.filestorage.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.scarlet.filestorage.dto.AttachmentPackageResponse
import ru.scarlet.filestorage.service.AttachmentPackageService
import java.util.UUID

@RestController
@RequestMapping("/package")
open class AttachmentPackageController(private val attachmentPackageService: AttachmentPackageService) {


    @GetMapping("/{uuid}")
    fun getPackageByUUID(@PathVariable uuid: UUID, httpServletRequest: HttpServletRequest): ResponseEntity<AttachmentPackageResponse> {
        val byPackageUUID: AttachmentPackageResponse = attachmentPackageService.getByPackageUUID(uuid, httpServletRequest)
        return ResponseEntity(byPackageUUID, HttpStatus.OK)
    }
}