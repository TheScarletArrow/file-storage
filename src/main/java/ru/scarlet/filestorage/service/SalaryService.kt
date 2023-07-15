package ru.scarlet.filestorage.service

import jakarta.servlet.http.HttpServletRequest
import ru.scarlet.filestorage.dto.SalaryIn
import ru.scarlet.filestorage.dto.SalaryOut
import ru.scarlet.filestorage.entity.Salary

interface SalaryService  {
    fun getSalaryByUserName(user: String): List<Salary>
    fun addSalary (salaryIn: SalaryIn, httpServletRequest: HttpServletRequest) : SalaryOut
    fun writeToExcel(httpServletRequest: HttpServletRequest): ByteArray
}