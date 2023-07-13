package ru.scarlet.filestorage.filter;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@NoArgsConstructor
@Data
@Configuration(value="JwtConfig")
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
    private String secretKey;
    private String tokenPrefix;
    private Integer accessTokenExpirationAfterDays;
    private Integer refreshTokenExpirationAfterDays;


    public String getAuthHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}
