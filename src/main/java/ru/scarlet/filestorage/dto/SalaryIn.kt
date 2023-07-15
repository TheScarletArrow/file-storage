package ru.scarlet.filestorage.dto

import lombok.Data
import ru.scarlet.filestorage.entity.Salary
import java.math.BigDecimal
import java.time.LocalDate

@Data

data class SalaryIn(val date: LocalDate, val amount: BigDecimal){


}
@Data
data class SalaryOut(val date: LocalDate, val amount: BigDecimal){

}