package moe.scarlet.azure_take_out_kt.extension

import com.baomidou.mybatisplus.core.metadata.IPage
import moe.scarlet.azure_take_out_kt.pojo.QueryResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.security.MessageDigest

/**
 * 代替Lombok的@Slf4j注解, 对每个类生成一个单例的Logger
 */
inline val <reified T> T.logger: Logger
    get() = LoggerFactory.getLogger(T::class.java)

/**
 * 加密使用
 */
private val hexDigits = "0123456789abcdef".toCharArray()

fun ByteArray.toHexString() = buildString(this.size shl 1) {
    this@toHexString.forEach { byte ->
        append(hexDigits[byte.toInt() ushr 4 and 15])
        append(hexDigits[byte.toInt() and 15])
    }
}

fun String.toMD5(): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(this.toByteArray())
    return digest.toHexString()
}

fun <T> IPage<T>.asQueryResult() = QueryResult<T>(this.total, this.records)