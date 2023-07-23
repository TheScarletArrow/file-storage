package ru.scarlet.filestorage.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.scarlet.filestorage.dto.SignInRequest
import ru.scarlet.filestorage.dto.SignInResponse
import ru.scarlet.filestorage.service.SignInService

@RestController
class SignInController(private val signInService: SignInService) {
    @RequestMapping("/signin")
    fun signIn(@RequestBody signInRequest: SignInRequest, httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse) : ResponseEntity<SignInResponse>{
        val signIn = signInService.signIn(signInRequest, httpServletRequest, httpServletResponse)
        return ResponseEntity.ok().body(signIn)
    }
}