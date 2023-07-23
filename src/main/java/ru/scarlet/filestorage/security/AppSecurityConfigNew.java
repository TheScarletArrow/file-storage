/*
 * Copyright (c) 2022. Program made by Anton Yurkov.
 */

package ru.scarlet.filestorage.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.scarlet.filestorage.filter.JwtConfig;
import ru.scarlet.filestorage.filter.CustomAuthenticationFilter;
import ru.scarlet.filestorage.filter.CustomAuthorizationFilter;
import ru.scarlet.filestorage.repository.LoginAttemptRepository;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;


@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class AppSecurityConfigNew {

//    public AppSecurityConfigNew(PasswordEncoder bCryptPasswordEncoder,
//                                UserDetailsService userDetailsService, JwtConfig jwtConfig, JwtSecretKey jwtSecretKey,
//                                UserLoginsRepository userLoginsRepository) {
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.userDetailsService = userDetailsService;
//        this.jwtConfig = jwtConfig;
////        this.jwtService = jwtService;
//        this.userLoginsRepository =  userLoginsRepository;
//    }

    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsService userDetailsService;
        private final LoginAttemptRepository loginAttemptRepository;
    private final JwtConfig jwtConfig;

    private final RedisTemplate<String, String> redisTemplate;


    //    private final JWTService jwtService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        var authenticationManager = builder.build();

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager, jwtConfig, redisTemplate, loginAttemptRepository);

        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
        http.authorizeHttpRequests(request -> request.requestMatchers(("/login/**")).permitAll());
        http.authorizeHttpRequests(request -> request.requestMatchers(("/signin/**")).permitAll());

        http.authorizeHttpRequests(request -> request.requestMatchers(("/api/token/refresh")).permitAll());
        http.authorizeHttpRequests(request -> request.requestMatchers("/users/**").permitAll());
        http.authorizeHttpRequests(request -> request.requestMatchers("/users/void1/").permitAll());

        http.authorizeHttpRequests(request -> request.requestMatchers(GET, "/api/v1/**").hasAnyAuthority("ROLE_USER"));
        http.authorizeHttpRequests(request -> request.requestMatchers(POST, "/api/v1/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER"));


        http.authorizeHttpRequests(req -> req.anyRequest().authenticated());
        http.authenticationManager(authenticationManager);
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

}
