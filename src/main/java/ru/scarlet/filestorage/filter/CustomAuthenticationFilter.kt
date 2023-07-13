package ru.scarlet.filestorage.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.AllArgsConstructor
import org.joda.time.DateTime
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.scarlet.filestorage.entity.LoginAttempt
import ru.scarlet.filestorage.repository.LoginAttemptRepository
import java.io.IOException
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import java.util.stream.*

@AllArgsConstructor
class CustomAuthenticationFilter(
    private val authenticationManager: AuthenticationManager? = null,
    private val jwtConfig: JwtConfig? = null,
    private val redisTemplate: RedisTemplate<String, String?>? = null,
    private val loginAttemptRepository: LoginAttemptRepository
) : UsernamePasswordAuthenticationFilter() {

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val username = request.getParameter("username")
        val password = request.getParameter("password")
        val token = UsernamePasswordAuthenticationToken(username, password)
        loginAttemptRepository.save(LoginAttempt(userName=username, dateTime = LocalDateTime.now(), ip = request.remoteAddr))
        return authenticationManager!!.authenticate(token)
    }

    @Throws(IOException::class, ServletException::class)
    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        logger.error("Authentication failed: " + failed.message)
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        super.unsuccessfulAuthentication(request, response, failed)
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val user = authResult.principal as User
        val algorithm = Algorithm.HMAC256(jwtConfig!!.secretKey.toByteArray())
        val date = Date()
        if (redisTemplate!!.opsForValue()[user.username + "_ACCESS"] == null) {
            val plusDay1 = DateTime(date).plusDays(jwtConfig.accessTokenExpirationAfterDays!!.toInt())
            val plusDays30 = DateTime(date).plusDays(30)
            val accessToken = JWT.create()
                .withSubject(user.username)
                .withExpiresAt(plusDay1.toDate())
                .withIssuer(request.requestURL.toString())
                .withClaim("roles", user.authorities.stream().map { obj: GrantedAuthority -> obj.authority }
                    .collect(Collectors.toList()))
                .sign(algorithm)
            val refreshToken = JWT.create()
                .withSubject(user.username)
                .withExpiresAt(plusDays30.toDate())
                .withIssuer(request.requestURL.toString())
                .sign(algorithm)
            val tokens: MutableMap<String, String> = HashMap()
            tokens["access_token"] = accessToken
            tokens["refresh_token"] = refreshToken
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            //        jwtService.save(user.getUsername(), access_token, refresh_token, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().get(0), plusDay1.toDate());
            ObjectMapper().writeValue(response.outputStream, tokens)
            val authenticatedUser: Authentication = UsernamePasswordAuthenticationToken(
                authResult.principal, authResult.credentials, authResult.authorities
            )
            SecurityContextHolder.getContext().authentication = authenticatedUser
            redisTemplate.opsForValue()[user.username + "_ACCESS", accessToken] =
                Duration.ofDays(jwtConfig.accessTokenExpirationAfterDays!!)
            redisTemplate.opsForValue()[user.username + "_REFRESH", refreshToken] =
                Duration.ofDays(jwtConfig.accessTokenExpirationAfterDays!!)

        } else {
            val plusDay1 = DateTime(date).plusDays(jwtConfig.accessTokenExpirationAfterDays!!.toInt())
            val plusDays30 = DateTime(date).plusDays(30)
            val accessTokenFromRedis = redisTemplate.opsForValue()[user.username + "_ACCESS"]
            val accessToken = accessTokenFromRedis
                ?: JWT.create()
                    .withSubject(user.username)
                    .withExpiresAt(plusDay1.toDate())
                    .withIssuer(request.requestURL.toString())
                    .withClaim("roles", user.authorities.stream().map { obj: GrantedAuthority -> obj.authority }
                        .collect(Collectors.toList()))
                    .sign(algorithm)
            val refreshTokenFromRedis = redisTemplate.opsForValue()[user.username + "_REFRESH"]
            val refreshToken = refreshTokenFromRedis
                ?: JWT.create()
                    .withSubject(user.username)
                    .withExpiresAt(plusDays30.toDate())
                    .withIssuer(request.requestURL.toString())
                    .sign(algorithm)
            val tokens: MutableMap<String, String> = HashMap()
            tokens["access_token"] = accessToken
            tokens["refresh_token"] = refreshToken
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            //        jwtService.save(user.getUsername(), access_token, refresh_token, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().get(0), plusDay1.toDate());
            ObjectMapper().writeValue(response.outputStream, tokens)
            val authenticatedUser: Authentication = UsernamePasswordAuthenticationToken(
                authResult.principal, authResult.credentials, authResult.authorities
            )
            SecurityContextHolder.getContext().authentication = authenticatedUser
        }
    }
}
