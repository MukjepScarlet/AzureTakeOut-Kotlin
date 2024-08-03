package moe.scarlet.azure_take_out_kt.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Configuration
class DateTimeConfig {

    /** 默认日期时间格式  */
    private val DEFAULT_DATE_TIME_FORMAT: String = "yyyy-MM-dd HH:mm:ss"

    /** 默认日期格式  */
    private val DEFAULT_DATE_FORMAT: String = "yyyy-MM-dd"

    /** 默认时间格式  */
    private val DEFAULT_TIME_FORMAT: String = "HH:mm:ss"

    @Bean
    fun objectMapper(builder: Jackson2ObjectMapperBuilder) = ObjectMapper().apply {
        registerModule(ParameterNamesModule())
        registerModule(
            JavaTimeModule().apply {
                addSerializer(
                    LocalDateTime::class.java,
                    LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT))
                )
                addSerializer(
                    LocalDate::class.java,
                    LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT))
                )
                addSerializer(
                    LocalTime::class.java,
                    LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT))
                )
                addDeserializer(
                    LocalDateTime::class.java,
                    LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT))
                )
                addDeserializer(
                    LocalDate::class.java,
                    LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT))
                )
                addDeserializer(
                    LocalTime::class.java,
                    LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT))
                )
            }
        )
    }

}
