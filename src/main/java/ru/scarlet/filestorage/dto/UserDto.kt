package ru.scarlet.filestorage.dto

import lombok.Value
import java.io.Serializable

/**
 * DTO for [ru.scarlet.filestorage.entity.User]
 */
@Value
data class UserDto  (
    var login: String? = null,
    var password: String? = null,
    var name: String? = null,
    var lastName: String? = null,
    var patronymic: String? = null
) : Serializable