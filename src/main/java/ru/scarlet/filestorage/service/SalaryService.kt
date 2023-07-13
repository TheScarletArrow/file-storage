package ru.scarlet.filestorage.service

import ru.scarlet.filestorage.entity.Salary

interface SalaryService {
    fun getSalaryByUserName(user: String): List<Salary>
}