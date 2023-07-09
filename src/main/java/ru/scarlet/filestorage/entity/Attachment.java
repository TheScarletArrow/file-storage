package ru.scarlet.filestorage.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.scarlet.filestorage.AttachmentType;

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

    private String user;

    private LocalDateTime created = LocalDateTime.now();

    Integer attachmentTypeCode;

    Integer downloadsLeft = 10;
    public Attachment(String filename, String fileType, byte[] data, Integer attachmentTypeCode) {
        this.filename = filename;
        this.fileType = fileType;
        this.data = data;
        this.attachmentTypeCode = attachmentTypeCode;
    }
}
