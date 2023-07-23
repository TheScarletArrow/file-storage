package ru.scarlet.filestorage.utils

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class JwtUtils {

    companion object {
        private val redisTemplate: RedisTemplate<String, String>? = null
        fun getAccessTokenForUser(user: String): String? {
            return redisTemplate?.opsForValue()?.get(user + "_ACCESS")
        }
    }
}