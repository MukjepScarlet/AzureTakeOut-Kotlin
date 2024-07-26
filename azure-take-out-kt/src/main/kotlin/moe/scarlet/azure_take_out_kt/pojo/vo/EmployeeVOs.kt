package moe.scarlet.azure_take_out_kt.pojo.vo

import java.io.Serializable

data class EmployeeLoginVO(
    val id: Long,
    val userName: String, // 这个字段和数据库里大小写对不上!!
    val name: String,
    val token: String
) : Serializable
