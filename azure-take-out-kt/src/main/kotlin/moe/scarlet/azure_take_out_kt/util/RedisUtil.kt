package moe.scarlet.azure_take_out_kt.util

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisUtil(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    operator fun set(key: String, value: Any) = redisTemplate.opsForValue().set(key, value)

    operator fun get(key: String) = redisTemplate.opsForValue().get(key)

}