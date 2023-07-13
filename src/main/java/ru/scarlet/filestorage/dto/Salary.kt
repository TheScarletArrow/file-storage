package ru.scarlet.filestorage.dto

import lombok.Data
import java.math.BigDecimal

@Data
data class Salary(private val date: org.joda.time.LocalDate, private val amount: BigDecimal){

}
