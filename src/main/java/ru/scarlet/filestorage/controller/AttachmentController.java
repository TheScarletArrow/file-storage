package ru.scarlet.filestorage.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.scarlet.filestorage.dto.AllAttachments;
import ru.scarlet.filestorage.dto.AttachmentUploadResponse;
import ru.scarlet.filestorage.dto.ResponseData;
import ru.scarlet.filestorage.entity.Attachment;
import ru.scarlet.filestorage.enums.AttachmentType;
import ru.scarlet.filestorage.service.AttachmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping("/upload")
    public ResponseEntity<AttachmentUploadResponse> uploadFile(@RequestParam("file") List<MultipartFile> files,
                                                               @RequestParam AttachmentType attachmentType,
                                                               @RequestParam Boolean isInfiniteDownloads,
                                                               HttpServletRequest request,
                                                               @RequestParam Integer downloads
    ) {
        List<ResponseData> responseData = new ArrayList<>();
        UUID packageUUID = UUID.randomUUID();
        for (MultipartFile file: files) {
            Attachment attachment = attachmentService.saveAttachmenet(file, attachmentType, isInfiniteDownloads, request, downloads);
            attachmentService.saveAttachmentPackage(attachment.getUuid(), request, packageUUID);
            String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/attachments/download/").path(attachment.getUuid().toString()).toUriString();
            responseData.add(new ResponseData(attachment.getFilename(), downloadURL, file.getContentType(), file.getSize()));
        }
        AttachmentUploadResponse response = new AttachmentUploadResponse(packageUUID, responseData);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/download/{uuid}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String uuid) {
        Attachment attachment = attachmentService.getAttachement(uuid);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))

                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFilename() + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }

    @GetMapping("/")
    public ResponseEntity<List<AllAttachments>> getAllUserFiles() {
        var attachments = attachmentService.getByAuthor(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return ResponseEntity.ok(attachments);
    }


}
