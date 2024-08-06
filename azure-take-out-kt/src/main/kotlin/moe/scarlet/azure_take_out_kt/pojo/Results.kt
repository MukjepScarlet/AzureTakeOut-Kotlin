package moe.scarlet.azure_take_out_kt.pojo

import java.io.Serializable

// Result和kt的库撞名字了
data class JsonResult<T : Any?>(
    val code: Int, // 1 成功 ...
    val msg: String?, // null 成功
    val data: T?, // null 失败或无数据
) : Serializable {
    companion object {
        @JvmStatic
        fun <T : Any?> success(data: T? = null) = JsonResult(1, null, data)

        @JvmStatic
        fun error(msg: String) = JsonResult(0, msg, null)
    }
}

data class QueryResult<T>(
    val total: Long,
    val records: List<T>,
) : Serializable
