package ru.scarlet.filestorage.service.impl

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import ru.scarlet.filestorage.dto.AttachmentPackageResponse
import ru.scarlet.filestorage.dto.AttachmentUploadResponse
import ru.scarlet.filestorage.dto.ResponseData
import ru.scarlet.filestorage.entity.AttachmentPackage
import ru.scarlet.filestorage.repository.AttachmentPackageRepository
import ru.scarlet.filestorage.repository.AttachmentRepository
import ru.scarlet.filestorage.service.AttachmentPackageService
import java.util.*

@Service
class AttachmentPackageServiceImpl(
    private val attachmentPackageRepository: AttachmentPackageRepository,
    private val attachmentRepository: AttachmentRepository
) : AttachmentPackageService {
    override fun getByPackageUUID(
        uuid: UUID,
        httpServletRequest: HttpServletRequest
    ): AttachmentPackageResponse? {
        val findByLink: List<AttachmentPackage> = attachmentPackageRepository.findByLink(uuid.toString())
        val list: ArrayList<ResponseData> = ArrayList()
        findByLink.forEach {

            val uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/attachments/download/" + it.attachmentUuid).build().toUriString();
            val attachment = attachmentRepository.findById(it.attachmentUuid).get()
            list.add(ResponseData(attachment.filename, uri, attachment.fileType,
                attachment.data.size.toLong()
            ))
        }
        val attachmentUploadResponse = AttachmentUploadResponse()
        attachmentUploadResponse.packageUUID = uuid
        attachmentUploadResponse.list = list
        val response = AttachmentPackageResponse(uuid.toString(), attachments = attachmentUploadResponse)
        return response
    }

}