package ru.scarlet.filestorage.controller;

import java.time.LocalDateTime;

public record ResponseMessage(String message, String path, Integer code, LocalDateTime dateTime) {
}
