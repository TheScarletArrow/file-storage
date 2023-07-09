package ru.scarlet.filestorage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.scarlet.filestorage.AttachmentType;
import ru.scarlet.filestorage.dto.ResponseData;
import ru.scarlet.filestorage.entity.Attachment;
import ru.scarlet.filestorage.service.AttachmentService;

import java.util.Base64;

@RestController
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseData> uploadFile(@RequestParam("file")MultipartFile file, @RequestParam AttachmentType attachmentType){
        Attachment attachment = attachmentService.saveAttachmenet(file, attachmentType);
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(attachment.getUuid().toString()).toUriString();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseData(attachment.getFilename(),downloadURL, file.getContentType(), file.getSize()));
    }

    @GetMapping("/download/{uuid}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String uuid){
        Attachment attachment = attachmentService.getAttachement(uuid);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(attachment.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+attachment.getFilename()+"\"")
                .body(new ByteArrayResource(attachment.getData()));
    }
}
