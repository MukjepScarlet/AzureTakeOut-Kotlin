package moe.scarlet.azure_take_out_kt.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import moe.scarlet.azure_take_out_kt.extension.asURLSearchParams
import moe.scarlet.azure_take_out_kt.property.WeChatProperties
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component

// 登录获取openid地址
private const val LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session"

// 微信支付下单接口地址
private const val JSAPI_URL = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi"

// 申请退款接口地址
private const val REFUNDS_URL = "https://api.mch.weixin.qq.com/v3/refund/domestic/refunds"

@Component
class WeChatUtil(
    private val weChatProperties: WeChatProperties
) {

    private val httpClient = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    private val Map<String, Any?>.jsonString: String
        get() = json.encodeToString(this)

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
