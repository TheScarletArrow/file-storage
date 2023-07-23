package ru.scarlet.filestorage.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link ru.scarlet.filestorage.entity.User}
 */
@Value
public class UserDto implements Serializable {
    String login;
    String password;
    String name;
    String lastName;
    String patronymic;
}