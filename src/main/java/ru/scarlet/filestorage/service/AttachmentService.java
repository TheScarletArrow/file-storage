package ru.scarlet.filestorage.service;

import org.springframework.web.multipart.MultipartFile;
import ru.scarlet.filestorage.AttachmentType;
import ru.scarlet.filestorage.entity.Attachment;

public interface AttachmentService {
    Attachment saveAttachmenet(MultipartFile file, AttachmentType attachmentType);

    Attachment getAttachement(String uuid);
}
