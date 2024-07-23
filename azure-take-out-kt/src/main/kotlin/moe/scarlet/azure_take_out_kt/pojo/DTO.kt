package moe.scarlet.azure_take_out_kt.pojo

import io.swagger.annotations.ApiModel

@ApiModel("登录参数")
data class EmployeeLoginDTO(
    val username: String,
    val password: String
)


// TODO