package ru.scarlet.filestorage.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.Builder
import lombok.Data
import java.time.LocalDateTime
import java.util.UUID

@Data
@Table
@Entity
@Builder

data class LoginAttempt (
    @Id
    @GeneratedValue(generator = "uuid")
    val uuid: UUID = UUID.randomUUID(),

    val userName: String? = null,

    val dateTime: LocalDateTime = LocalDateTime.now(),

    val ip: String? = null


)