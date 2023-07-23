package ru.scarlet.filestorage.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import ru.scarlet.filestorage.dto.SignInRequest
import ru.scarlet.filestorage.dto.SignInResponse

interface SignInService {
    fun signIn(
        signInRequest: SignInRequest,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    ): SignInResponse
}