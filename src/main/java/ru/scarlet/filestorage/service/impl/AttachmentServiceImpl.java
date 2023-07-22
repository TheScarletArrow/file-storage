package ru.scarlet.filestorage.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.scarlet.filestorage.entity.AttachmentPackage;
import ru.scarlet.filestorage.enums.AttachmentType;
import ru.scarlet.filestorage.dto.AllAttachments;
import ru.scarlet.filestorage.entity.Attachment;
import ru.scarlet.filestorage.exception.AttachmentNotFoundException;
import ru.scarlet.filestorage.exception.HashDifferException;
import ru.scarlet.filestorage.exception.NoDownloadsLeftException;
import ru.scarlet.filestorage.exception.SomethingWentWrongException;
import ru.scarlet.filestorage.filter.JwtConfig;
import ru.scarlet.filestorage.repository.AttachmentPackageRepository;
import ru.scarlet.filestorage.repository.AttachmentRepository;
import ru.scarlet.filestorage.security.Helper;
import ru.scarlet.filestorage.service.AttachmentService;
import ru.scarlet.filestorage.utils.HashUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final JwtConfig jwtConfig;
    private final AttachmentPackageRepository attachmentPackageRepository;

    @Override
    public Attachment saveAttachmenet(MultipartFile file, AttachmentType attachmentType, Boolean isInfiniteDownloads, HttpServletRequest request) {
        try {
            String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String author = new Helper(jwtConfig).getUsernameFromToken(request.getHeader(AUTHORIZATION).replace(jwtConfig.getTokenPrefix(), ""));

            Attachment attachment = new Attachment(filename, file.getContentType(), file.getBytes(), attachmentType.getCode(),
                    isInfiniteDownloads,
                    author);
            attachment.setHash(HashUtils.Companion.createHash(file.getBytes()));
            return attachmentRepository.save(attachment);
        } catch (IOException e) {
            throw new SomethingWentWrongException();
        }
    }

    @Override
    public Attachment getAttachement(String uuid) {
        var attachment = attachmentRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new AttachmentNotFoundException("NOT FOUND"));
        if (!attachment.getHash().equalsIgnoreCase(HashUtils.Companion.createHash(attachment.getData())))
            throw new HashDifferException(" ERROR!!!!");
        if (attachment.getDownloadsLeft() == 0) throw new NoDownloadsLeftException();
        if (!attachment.getIsInfiniteDownloads())
            attachment.setDownloadsLeft(attachment.getDownloadsLeft() - 1);
        return attachmentRepository.save(attachment);

    }

    @Override
    public List<AllAttachments> getByAuthor(String authorFromToken) {
        List<AllAttachments> allAttachments = new ArrayList<>();
        attachmentRepository.findByAuthorOrderByCreatedDesc(authorFromToken).forEach(attachment -> allAttachments.add(new AllAttachments(attachment.getUuid(), attachment.getFilename(), attachment.getFileType(), attachment.getCreated(), attachment.getAttachmentTypeCode(), attachment.getAuthor(), attachment.getIsInfiniteDownloads(), attachment.getDownloadsLeft())));
        return allAttachments;
    }

    @Override
    public void saveAttachmentPackage(UUID uuid, HttpServletRequest request, UUID packageUUID) {
        UUID id = UUID.randomUUID();
        AttachmentPackage attachmentPackage = new AttachmentPackage(id, uuid, packageUUID.toString());
        attachmentPackageRepository.save(attachmentPackage);
    }
}
