package moe.scarlet.azure_take_out_kt.pojo.dto

import java.io.Serializable

data class UserDTO(
    val code: String,
    val location: String?, // 经纬度, 文档里没写
) : Serializable
