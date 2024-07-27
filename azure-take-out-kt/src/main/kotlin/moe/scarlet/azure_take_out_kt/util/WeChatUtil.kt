package moe.scarlet.azure_take_out_kt.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import moe.scarlet.azure_take_out_kt.extension.asURLSearchParams
import moe.scarlet.azure_take_out_kt.property.WeChatProperties
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component

@Component
class WeChatUtil(
    private val weChatProperties: WeChatProperties
) {

    private val httpClient = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    private val LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session"

    @Serializable
    data class LoginResult(
        val openid: String,
        @SerialName("session_key")
        val sessionKey: String,
        val unionid: String? = null,
        val errcode: Int = 0,
        val errmsg: String? = null,
    )

    suspend fun login(code: String): LoginResult? {
        val url = "$LOGIN_URL?" + mapOf(
            "appid" to weChatProperties.appid,
            "secret" to weChatProperties.secret,
            "js_code" to code,
            "grant_type" to "authorization_code",
        ).asURLSearchParams()

        val request = Request.Builder().url(url).build()

        return httpClient.newCall(request).execute().use { response ->
            if (response.isSuccessful)
                response.body?.string()?.let { json.decodeFromString<LoginResult>(it) }
            else
                null
        }
    }

}
