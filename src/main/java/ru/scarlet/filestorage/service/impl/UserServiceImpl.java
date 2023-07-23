package ru.scarlet.filestorage.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.scarlet.filestorage.dto.UserDto;
import ru.scarlet.filestorage.entity.Role;
import ru.scarlet.filestorage.entity.User;
import ru.scarlet.filestorage.exception.UserAlreadyExistsException;
import ru.scarlet.filestorage.repository.RoleRepository;
import ru.scarlet.filestorage.repository.UserRepository;
import ru.scarlet.filestorage.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User saveUser(UserDto userDto) {
        Optional<User> byLogin = userRepository.findByLogin(userDto.getLogin());
        if (byLogin.isPresent())
                throw new UserAlreadyExistsException(String.format("User %s already exists", userDto.getLogin()));
        User user = new User(userDto.getLogin(), passwordEncoder.encode(userDto.getPassword()), userDto.getName(), userDto.getLastName(), userDto.getPatronymic());
        return userRepository.save(user);

    }

    @Override
    public void saveUser(User user) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);

    }

    @Override
    public void saveRole(Role roleSuperAdmin) {
        roleRepository.save(roleSuperAdmin);
    }

    @Override
    public void addRoleToUser(String username, String roleUser) {
        User user = userRepository.findByLogin(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        Role role = roleRepository.findByName(roleUser);
        user.getRoles().add(role);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByLogin(username).orElseThrow(()->new RuntimeException(""));
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
//        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
//
//    }
@Override @Transactional

public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByLogin(username).get();
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
}

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//            User user = userRepository.findByLogin(username).orElseThrow(()->new RuntimeException(""));
//            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//            if (user.getRole()!=null)
//                authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
//            return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
//
//    }
}
