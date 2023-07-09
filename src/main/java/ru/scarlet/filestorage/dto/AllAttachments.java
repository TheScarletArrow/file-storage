package ru.scarlet.filestorage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllAttachments {
    private UUID attachmentUUID;

    private String filename;

    private String fileType;

    private LocalDateTime created;

    private Integer attachmentTypeCode;

    private String author;

    private Boolean isInfiniteDownloads;

    private Integer downloadsLeft;
}
