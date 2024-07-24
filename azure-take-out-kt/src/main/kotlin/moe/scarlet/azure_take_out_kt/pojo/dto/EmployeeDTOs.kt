package moe.scarlet.azure_take_out_kt.pojo.dto

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

@Schema(description = "员工分页查询参数")
data class EmployeePageQueryDTO(
    val name: String?,
    val page: Long,
    val pageSize: Long,
)

@Schema(description = "员工修改密码参数")
data class EmployeeEditPasswordDTO(
//    val empId: Long,
//    文档有问题, 这个字段不存在, 改为使用当前登录用户的ID
    val oldPassword: String,
    val newPassword: String,
)
