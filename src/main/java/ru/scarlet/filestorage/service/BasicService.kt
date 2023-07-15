package ru.scarlet.filestorage.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Autowired
import ru.scarlet.filestorage.filter.JwtConfig

open class BasicService() {
    @Autowired
    private lateinit var jwtConfig: JwtConfig
    fun getUsername(header: String): String {
        val algorithm: Algorithm = Algorithm.HMAC256(jwtConfig.secretKey.toByteArray())
        val verifier = JWT.require(algorithm).build()
        val decodedJWT: DecodedJWT = verifier.verify(header.replace(jwtConfig.tokenPrefix!!, ""))
        return decodedJWT.subject
    }
}