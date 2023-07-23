package ru.scarlet.filestorage.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import ru.scarlet.filestorage.enums.AttachmentType;
import ru.scarlet.filestorage.dto.AllAttachments;
import ru.scarlet.filestorage.entity.Attachment;

import java.util.List;
import java.util.UUID;

public interface AttachmentService {
    Attachment saveAttachmenet(MultipartFile file, AttachmentType attachmentType, Boolean isInfiniteDownloads, HttpServletRequest request, Integer downloads);

    Attachment getAttachement(String uuid);

    List<AllAttachments> getByAuthor(String authorFromToken);

    void saveAttachmentPackage(UUID uuid, HttpServletRequest request, UUID packageUUID);
}
