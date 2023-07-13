package ru.scarlet.filestorage.service.impl

import org.springframework.stereotype.Service
import ru.scarlet.filestorage.entity.Salary
import ru.scarlet.filestorage.repository.SalaryRepository
import ru.scarlet.filestorage.service.SalaryService

@Service
open class SalaryServiceImpl(val salaryRepository: SalaryRepository) : SalaryService {
    override fun getSalaryByUserName(user: String): List<Salary> {
        return salaryRepository.findByUserNameOrderByDateDesc(user)
    }

}