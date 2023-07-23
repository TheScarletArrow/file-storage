package ru.scarlet.filestorage.service.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.joda.time.DateTime
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.scarlet.filestorage.dto.SignInRequest
import ru.scarlet.filestorage.dto.SignInResponse
import ru.scarlet.filestorage.exception.PasswordMismatchException
import ru.scarlet.filestorage.filter.JwtConfig
import ru.scarlet.filestorage.repository.UserRepository
import ru.scarlet.filestorage.service.SignInService
import java.time.Duration
import java.util.*
import java.util.stream.*

@Service
open class SignInServiceImpl(
                             private val redisTemplate: RedisTemplate<String, String?>? = null,
                             private val jwtConfig: JwtConfig? = null,
    private val userServiceImpl: UserServiceImpl,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder

                             ) : SignInService {
    override fun signIn(
        signInRequest: SignInRequest,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    ): SignInResponse {
        val username = signInRequest.username
        val password = signInRequest.password

        val userByUsername = userServiceImpl.loadUserByUsername(username)

        val user = userRepository.findByLogin(username)?.get()
        if (!passwordEncoder.matches(password, user!!.password))
            throw PasswordMismatchException("PASSWORD ERRR")
        user!!.roles.forEach { it->SimpleGrantedAuthority(it.name) }

        val algorithm = Algorithm.HMAC256(jwtConfig!!.secretKey.toByteArray())
        val date = Date()
        if (redisTemplate!!.opsForValue()[username + "_ACCESS"] == null) {
            val plusDay1 = DateTime(date).plusDays(jwtConfig.accessTokenExpirationAfterDays!!.toInt())
            val plusDays30 = DateTime(date).plusDays(30)
            val accessToken = JWT.create()
                .withSubject(userByUsername.username)
                .withExpiresAt(plusDay1.toDate())
                .withIssuer(httpServletRequest.requestURL.toString())
                .withClaim("roles", userByUsername.authorities.stream().map { obj: GrantedAuthority -> obj.authority }
                    .collect(Collectors.toList()))
                .sign(algorithm)
            val refreshToken = JWT.create()
                .withSubject(userByUsername.username)
                .withExpiresAt(plusDays30.toDate())
                .withIssuer(httpServletRequest.requestURL.toString())
                .sign(algorithm)
            val tokens: MutableMap<String, String> = HashMap()
            tokens["access_token"] = accessToken
            tokens["refresh_token"] = refreshToken
            httpServletResponse.contentType = MediaType.APPLICATION_JSON_VALUE
            //        jwtService.save(user.getUsername(), access_token, refresh_token, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().get(0), plusDay1.toDate());
            ObjectMapper().writeValue(httpServletResponse.outputStream, tokens)


            val authenticatedUser: Authentication = UsernamePasswordAuthenticationToken(
                userByUsername.username, password, userByUsername.authorities
            )
            SecurityContextHolder.getContext().authentication = authenticatedUser
            redisTemplate.opsForValue()[userByUsername.username + "_ACCESS", accessToken] =
                Duration.ofDays(jwtConfig.accessTokenExpirationAfterDays!!)
            redisTemplate.opsForValue()[userByUsername.username + "_REFRESH", refreshToken] =
                Duration.ofDays(jwtConfig.accessTokenExpirationAfterDays!!)
            return SignInResponse(accessToken, refreshToken)

        } else {
            val plusDay1 = DateTime(date).plusDays(jwtConfig.accessTokenExpirationAfterDays!!.toInt())
            val plusDays30 = DateTime(date).plusDays(30)
            val accessTokenFromRedis = redisTemplate.opsForValue()[userByUsername.username + "_ACCESS"]
            val accessToken = accessTokenFromRedis
                ?: JWT.create()
                    .withSubject(userByUsername.username)
                    .withExpiresAt(plusDay1.toDate())
                    .withIssuer(httpServletRequest.requestURL.toString())
                    .withClaim(
                        "roles",
                        userByUsername.authorities.stream().map { obj: GrantedAuthority -> obj.authority }
                            .collect(Collectors.toList()))
                    .sign(algorithm)
            val refreshTokenFromRedis = redisTemplate.opsForValue()[userByUsername.username + "_REFRESH"]
            val refreshToken = refreshTokenFromRedis
                ?: JWT.create()
                    .withSubject(userByUsername.username)
                    .withExpiresAt(plusDays30.toDate())
                    .withIssuer(httpServletRequest.requestURL.toString())
                    .sign(algorithm)
            val tokens: MutableMap<String, String> = HashMap()
            tokens["access_token"] = accessToken
            tokens["refresh_token"] = refreshToken
            httpServletResponse.contentType = MediaType.APPLICATION_JSON_VALUE
            //        jwtService.save(user.getUsername(), access_token, refresh_token, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().get(0), plusDay1.toDate());
            ObjectMapper().writeValue(httpServletResponse.outputStream, tokens)
            val authenticatedUser: Authentication = UsernamePasswordAuthenticationToken(
                userByUsername.username, password, userByUsername.authorities
            )
            SecurityContextHolder.getContext().authentication = authenticatedUser
            return SignInResponse(accessToken, refreshToken)

        }
    }
}