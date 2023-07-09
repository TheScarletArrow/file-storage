package ru.scarlet.filestorage;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum AttachmentType {
    DOCUMENT(0, "DOCUMENT"),
    PERSONAL(1, "PERSONAL"),
    PUBLIC(2, "PUBLIC");

    private int code;
    private String attachmentType;
    AttachmentType(int code, String attachemntType) {
        this.code=code;
        this.attachmentType=attachemntType;
    }

    private AttachmentType getByCode(int code){
        if (code==DOCUMENT.getCode()) return AttachmentType.DOCUMENT;
        if (code== PERSONAL.getCode()) return AttachmentType.PERSONAL;
        if (code== PUBLIC.getCode()) return AttachmentType.PUBLIC;
        return null;
    }
}
