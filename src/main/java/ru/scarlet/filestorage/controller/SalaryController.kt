package ru.scarlet.filestorage.controller

import jakarta.servlet.http.HttpServletRequest
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.scarlet.filestorage.dto.SalaryIn
import ru.scarlet.filestorage.dto.SalaryOut
import ru.scarlet.filestorage.security.Helper
import ru.scarlet.filestorage.service.SalaryService

@RestController
@RequestMapping("/salary")
@Slf4j
open class SalaryController(private val salaryService: SalaryService) {

    @GetMapping("/")
    fun getSalary(httpServletRequest: HttpServletRequest): ResponseEntity<List<SalaryOut>> {

        val salaryOutList: List<SalaryOut> =
            salaryService.getSalaryByUserName(SecurityContextHolder.getContext().authentication.principal.toString())
                .map {
                    SalaryOut(it.date, it.amount)
                }
        return ResponseEntity.ok(salaryOutList)
    }
}