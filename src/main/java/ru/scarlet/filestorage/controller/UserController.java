package ru.scarlet.filestorage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.scarlet.filestorage.dto.UserDto;
import ru.scarlet.filestorage.entity.Role;
import ru.scarlet.filestorage.entity.User;
import ru.scarlet.filestorage.service.UserService;

import java.util.ArrayList;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto) {
        User user = userService.saveUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @GetMapping("/void1/")
    public void voidMethod() {

        userService.saveRole((new Role(null, "ROLE_USER")));
        userService.saveRole((new Role(null, "ROLE_MANAGER")));
        userService.saveRole((new Role(null, "ROLE_ADMIN")));
        userService.saveRole((new Role(null, "ROLE_SUPER_ADMIN")));

        final User user1 = new User(null, "John Travolta", "adyurkov", "None",  "1234", "anton@anton.ru", new ArrayList<>());
        userService.saveUser(user1);
        final User user2 = new User(null, "John Travolta", "g", "None",  "1234", "anton@anton.ru", new ArrayList<>());
        userService.saveUser(user2);
        final User user3 = new User(null, "John Travolta", "jim", "None",  "1234", "anton@anton.ru", new ArrayList<>());
        userService.saveUser(user3);
        final User user4 = new User(null, "John Travolta", "arnold", "None",  "1234", "anton@anton.ru", new ArrayList<>());
        userService.saveUser(user4);


        userService.addRoleToUser("adyurkov", "ROLE_USER");
        userService.addRoleToUser("will", "ROLE_MANAGER");
        userService.addRoleToUser("jim", "ROLE_ADMIN");
        userService.addRoleToUser("adyurkov", "ROLE_SUPER_ADMIN");
        userService.addRoleToUser("arnold", "ROLE_ADMIN");
        userService.addRoleToUser("arnold", "ROLE_USER");
    }

        @GetMapping("/currentUser")
    public ResponseEntity<Object> getCurrent(){
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication());
    }
}
