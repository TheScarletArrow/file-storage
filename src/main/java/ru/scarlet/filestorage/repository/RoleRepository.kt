package ru.scarlet.filestorage.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.scarlet.filestorage.entity.Role

interface RoleRepository : JpaRepository<Role?, Long?> {
    fun findByName(name: String?): Role?
}
