/*
 * Copyright (c) 2022. Program made by Anton Yurkov.
 */

package ru.scarlet.filestorage.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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



@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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

    private final JwtConfig jwtConfig;




//    private final JWTService jwtService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        var authenticationManager = builder.build();

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager, jwtConfig);

        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
        http.authorizeHttpRequests(request->request.requestMatchers(("/login/**")).permitAll());
        http.authorizeHttpRequests(request->request.requestMatchers(( "/api/token/refresh")).permitAll());
        http.authorizeHttpRequests(request->request.requestMatchers("/users/").permitAll());
        http.authorizeHttpRequests(request->request.requestMatchers("/users/void1/").permitAll());

//        http.authorizeRequests().antMatchers(GET,"/api/v1/**").hasAnyAuthority("ROLE_USER");
//        http.authorizeRequests().antMatchers(POST,"/api/v1/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER");
//        http.authorizeRequests().antMatchers("/graphiql/**").permitAll();
//        http.authorizeRequests().antMatchers("/graphql/**").permitAll();
//        http.authorizeRequests().antMatchers("/voyager/**").permitAll();
//        http.authorizeRequests().antMatchers("/playground/**").permitAll();

       //fixme

        http.authorizeHttpRequests(req-> req.anyRequest().authenticated());
        http.authenticationManager(authenticationManager);
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    
}
