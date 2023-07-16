package ru.scarlet.filestorage.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import ru.scarlet.filestorage.enums.AttachmentType;
import ru.scarlet.filestorage.dto.AllAttachments;
import ru.scarlet.filestorage.entity.Attachment;

import java.util.List;

public interface AttachmentService {
    Attachment saveAttachmenet(MultipartFile file, AttachmentType attachmentType, Boolean isInfiniteDownloads, HttpServletRequest request);

    Attachment getAttachement(String uuid);

    List<AllAttachments> getByAuthor(String authorFromToken);
}
