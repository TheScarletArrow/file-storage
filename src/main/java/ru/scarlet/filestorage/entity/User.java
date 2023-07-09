package ru.scarlet.filestorage.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Getter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private UUID uuid = UUID.randomUUID();

    private String login;

    private String password;

    private String name;

    private String lastName;

    private String patronymic;
    private String email;


    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> roles = new ArrayList<>();

    public User(String login, String password, String name, String lastName, String patronymic) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.patronymic = patronymic;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(Integer id, String name, String username, String middleName,  String email) {
        this.id = id;
        this.name = name;
        this.login = username;
        this.lastName = middleName;
        this.email = email;

    }

    public User(Integer id, String name, String username,  String middleName, String password, String email, List<Role> roleList) {
        this.id = id;
        this.name = name;
        this.login = username;
        this.lastName = middleName;
        this.password = password;
        this.email = email;
        this.roles = roleList;

    }
}
