package ru.scarlet.filestorage.controller

import jakarta.servlet.http.HttpServletRequest
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.scarlet.filestorage.dto.SalaryIn
import ru.scarlet.filestorage.dto.SalaryOut
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

    @PostMapping("/")
    fun postSalary(@RequestBody salaryIn: SalaryIn, httpServletRequest: HttpServletRequest): ResponseEntity<*> {
        val salary = salaryService.addSalary(salaryIn, httpServletRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(salary)
    }

    @PostMapping("/excel")
    fun salaryToExcel(httpServletRequest: HttpServletRequest) : ResponseEntity<ByteArray> {
        val report = salaryService.writeToExcel(httpServletRequest)
        val fileName = "report.xlsx"
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$fileName\"")
            .body(report)
    }
}