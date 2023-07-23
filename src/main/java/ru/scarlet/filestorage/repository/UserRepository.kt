package ru.scarlet.filestorage.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.scarlet.filestorage.entity.User
import java.util.*

@Repository
interface UserRepository : JpaRepository<User?, Int?> {
    fun findByLogin(login: String?): Optional<User?>?
}