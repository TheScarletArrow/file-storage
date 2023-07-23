package ru.scarlet.filestorage.dto

data class SignInRequest(val username: String?, val password: String?)

data class SignInResponse(
    val accessToken: String?,
    val refreshToken: String?
)