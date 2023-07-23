package ru.scarlet.filestorage.service;

import ru.scarlet.filestorage.dto.UserDto;
import ru.scarlet.filestorage.entity.Role;
import ru.scarlet.filestorage.entity.User;

public interface UserService {

    public User saveUser(UserDto userDto);

    void saveUser(User user);

    void saveRole(Role roleSuperAdmin);

    void addRoleToUser(String adyurkov, String roleUser);
}
