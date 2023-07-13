package ru.scarlet.filestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository
import ru.scarlet.filestorage.entity.LoginAttempt
import java.util.*

interface LoginAttemptRepository : JpaRepository<LoginAttempt, UUID> {
}