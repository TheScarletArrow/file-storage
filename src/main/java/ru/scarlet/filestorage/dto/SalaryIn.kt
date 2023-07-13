package ru.scarlet.filestorage.dto

import lombok.Data
import java.math.BigDecimal

@Data
data class SalaryIn(private val date: org.joda.time.LocalDate, private val amount: BigDecimal){

}
@Data
data class SalaryOut(private val date: org.joda.time.LocalDate, private val amount: BigDecimal){

}