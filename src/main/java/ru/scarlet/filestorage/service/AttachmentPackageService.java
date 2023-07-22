package ru.scarlet.filestorage.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.scarlet.filestorage.dto.AttachmentPackageResponse;

import java.util.UUID;

public interface AttachmentPackageService {
    AttachmentPackageResponse getByPackageUUID(UUID uuid, HttpServletRequest httpServletRequest);
}
