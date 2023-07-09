package ru.scarlet.filestorage.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID uuid;

    private String filename;

    private String fileType;

    @Lob
    @Column(columnDefinition = "longblob")
    @JdbcTypeCode(SqlTypes.BLOB)
    private byte[] data;


    private LocalDateTime created = LocalDateTime.now();

    Integer attachmentTypeCode;

    String author;
    Boolean isInfiniteDownloads;
    Integer downloadsLeft = 10;
    public Attachment(String filename, String fileType, byte[] data, Integer attachmentTypeCode, Boolean isInfiniteDownloads, String author) {
        this.filename = filename;
        this.fileType = fileType;
        this.data = data;
        this.attachmentTypeCode = attachmentTypeCode;
        this.isInfiniteDownloads=isInfiniteDownloads;
        this.author=author;
    }
}
