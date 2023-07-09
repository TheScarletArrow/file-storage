package ru.scarlet.filestorage.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.scarlet.filestorage.filter.JwtConfig;

@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class Helper {

    private final JwtConfig jwtConfig;
    public  String getUsernameFromToken(String token){

        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecretKey().getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String subject = decodedJWT.getSubject();
        log.info(subject);
        return subject;
    }


}
