package ru.scarlet.filestorage.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.scarlet.filestorage.AttachmentType;
import ru.scarlet.filestorage.entity.Attachment;
import ru.scarlet.filestorage.exception.NoDownloadsLeftException;
import ru.scarlet.filestorage.repository.AttachmentRepository;
import ru.scarlet.filestorage.service.AttachmentService;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    @Override
    public Attachment saveAttachmenet(MultipartFile file, AttachmentType attachmentType) {
        try {
            String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            Attachment attachment = new Attachment(filename, file.getContentType(), file.getBytes(), attachmentType.getCode());
            return attachmentRepository.save(attachment);
        } catch (IOException e){
            throw new RuntimeException();
        }
    }

    @Override
    public Attachment getAttachement(String uuid) {
        var attachment =  attachmentRepository.findById(UUID.fromString(uuid)).orElseThrow(()->new RuntimeException("NOT FOUND"));
        if (attachment.getDownloadsLeft()==0) throw new NoDownloadsLeftException();
        attachment.setDownloadsLeft(attachment.getDownloadsLeft()-1);
        return  attachmentRepository.save(attachment);

    }
}
