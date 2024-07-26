package moe.scarlet.azure_take_out_kt.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.*
import java.time.Duration

@Configuration
class RedisConfig {

    @Bean
    fun redisConnectionFactory() = LettuceConnectionFactory()

    @Bean
    fun redisTemplate() = RedisTemplate<String, Any>().apply {
        connectionFactory = redisConnectionFactory()
        valueSerializer = GenericToStringSerializer(Any::class.java)
    }

    @Bean
    fun redisCacheConfiguration() = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(5)) // 过期时间
        .disableCachingNullValues() // 禁止缓存空值

}
