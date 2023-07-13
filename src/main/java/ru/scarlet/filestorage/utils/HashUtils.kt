package ru.scarlet.filestorage.utils

import org.springframework.stereotype.Component
import java.math.BigInteger
import java.security.MessageDigest

@Component
class HashUtils {
    companion object {
        fun createHash(array: ByteArray): String {
            val hash = MessageDigest.getInstance("MD5").digest(array)
            return BigInteger(1, hash).toString(16);
        }
    }
}