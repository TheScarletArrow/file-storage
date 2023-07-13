package ru.scarlet.filestorage.utils

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class JwtUtils {
    private val redisTemplate: RedisTemplate<String, String>? = null

    fun getAccessTokenForUser(): Unit {
        
    }
}