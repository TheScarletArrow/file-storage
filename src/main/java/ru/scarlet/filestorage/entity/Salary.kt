package ru.scarlet.filestorage.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.EqualsAndHashCode
import lombok.ToString
import org.joda.time.LocalDate
import ru.scarlet.filestorage.dto.SalaryOut
import java.math.BigDecimal
import java.util.*

@Table
@Entity
@ToString
@EqualsAndHashCode
@AllArgsConstructor
class Salary {
    fun toSalaryOut(): SalaryOut {
        return SalaryOut(date=this.date, amount=this.amount)
    }

    @Id
    @GeneratedValue(generator = "uuid")
    val uuid: UUID = UUID.randomUUID()

    var userName: String? = null

    @Column(columnDefinition = "DATE")
    var date: java.time.LocalDate = java.time.LocalDate.now()

    var amount: BigDecimal = BigDecimal.ZERO

    var companyName : String? = null
}


