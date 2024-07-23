package moe.scarlet.azure_take_out_kt.controller

import moe.scarlet.azure_take_out_kt.constant.JwtClaimsConstant
import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.pojo.EmployeeLoginDTO
import moe.scarlet.azure_take_out_kt.pojo.EmployeeLoginVO
import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.property.JwtProperties
import moe.scarlet.azure_take_out_kt.service.EmployeeService
import moe.scarlet.azure_take_out_kt.util.JwtUtil
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/employee")
class EmployeeController(
    private val employeeService: EmployeeService,
    private val jwtProperties: JwtProperties
) {

    @PostMapping("/login")
    fun login(@RequestBody employeeLoginDTO: EmployeeLoginDTO): JsonResult<EmployeeLoginVO> {
        logger.info("员工登录: $employeeLoginDTO")

        val (id, name, username) = employeeService.login(employeeLoginDTO)

        // 登录成功后，生成jwt令牌
        val token: String = JwtUtil.createJWT(
            jwtProperties.adminSecretKey,
            jwtProperties.adminTTL,
            mapOf(JwtClaimsConstant.EMP_ID to id)
        )

        return JsonResult.success(EmployeeLoginVO(id, username, name, token))
    }

    @PostMapping("/logout")
    fun logout(): JsonResult<Nothing> = JsonResult.success()

}