package moe.scarlet.azure_take_out_kt.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * 使用新版jwt库(0.12.5)接口
 */
object JwtUtil {

    private fun String.toSecretKey() = Keys.hmacShaKeyFor(this.toByteArray(StandardCharsets.UTF_8))

    @JvmStatic
    fun createJWT(secretKey: String, ttlMillis: Long, claims: Map<String, Any>): String {
        val uuid = UUID.randomUUID().toString()

        return Jwts.builder()
            .header()
            .add("typ", "JWT")
            .add("alg", "HS256")
            .and()
            .claims(claims)
            .id(uuid)
            .expiration(Date(System.currentTimeMillis() + ttlMillis))
            // .subject 主题
            // ,issuer 签发者
            .signWith(secretKey.toSecretKey(), Jwts.SIG.HS256)
            .compact()
    }

    @JvmStatic
    fun parseJWT(secretKey: String, token: String): Claims {
        return Jwts.parser()
            .verifyWith(secretKey.toSecretKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

}