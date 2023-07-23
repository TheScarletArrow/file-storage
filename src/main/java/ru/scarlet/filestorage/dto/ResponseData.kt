package ru.scarlet.filestorage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {

    private String filename;

    private String downloadURL;

    private String fileType;

    private Long size;
}
