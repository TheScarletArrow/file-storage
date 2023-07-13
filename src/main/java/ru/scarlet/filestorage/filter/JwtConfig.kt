package ru.scarlet.filestorage.filter

import lombok.Data
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@NoArgsConstructor
@Data
@Configuration(value = "JwtConfig")
@ConfigurationProperties(prefix = "application.jwt")
@Getter
@Setter
class JwtConfig {
    lateinit var secretKey: String
     var tokenPrefix: String? = null
     var accessTokenExpirationAfterDays: Long? = null
     var refreshTokenExpirationAfterDays: Int? = null
    val authHeader: String
        get() = HttpHeaders.AUTHORIZATION

}
