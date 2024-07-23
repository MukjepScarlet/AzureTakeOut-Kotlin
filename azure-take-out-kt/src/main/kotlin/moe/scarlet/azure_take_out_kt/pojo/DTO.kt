package moe.scarlet.azure_take_out_kt.pojo

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "登录参数")
data class EmployeeLoginDTO(
    val username: String,
    val password: String
)

@Schema(description = "新增员工参数")
data class EmployeeDTO(
    val id: Long?,
    val username: String,
    val name: String,
    val phone: String,
    val sex: String,
    val idNumber: String
)
