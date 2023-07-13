package ru.scarlet.filestorage.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.Data
import lombok.EqualsAndHashCode
import lombok.Getter
import lombok.Setter
import lombok.ToString
import org.joda.time.LocalDate
import java.math.BigDecimal
import java.util.*

@Data
@Table
@Entity
@ToString
@EqualsAndHashCode

class Salary {

    @Id
    @GeneratedValue(generator = "uuid")
    val uuid: UUID = UUID.randomUUID()

    val userName: String? = null

    @Column(columnDefinition = "DATE")
    val date: org.joda.time.LocalDate = LocalDate()

    val amount: BigDecimal = BigDecimal.ZERO


}


