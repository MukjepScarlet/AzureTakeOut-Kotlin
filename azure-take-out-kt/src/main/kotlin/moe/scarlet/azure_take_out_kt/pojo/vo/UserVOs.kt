package moe.scarlet.azure_take_out_kt.pojo.vo

import java.io.Serializable

data class UserVO(
    val id: Long,
    val openid: String,
    val token: String,
) : Serializable
