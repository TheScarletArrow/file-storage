package ru.scarlet.filestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.scarlet.filestorage.entity.Salary
import java.time.LocalDate
import java.util.*

interface SalaryRepository : JpaRepository<Salary, UUID> {


    fun findByUserNameOrderByDateAsc(userName: String): List<Salary>


    fun findByCompanyName(companyName: String): List<Salary>


    fun findByCompanyNameIgnoreCaseAndDate(companyName: String, date: LocalDate): List<Salary>


}
