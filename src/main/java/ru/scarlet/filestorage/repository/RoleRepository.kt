package ru.scarlet.filestorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.scarlet.filestorage.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

}

