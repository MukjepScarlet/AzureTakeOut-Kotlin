package moe.scarlet.azure_take_out_kt.controller.admin

import io.swagger.v3.oas.annotations.Operation
import moe.scarlet.azure_take_out_kt.constant.JwtClaimsConstant
import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.pojo.*
import moe.scarlet.azure_take_out_kt.property.JwtProperties
import moe.scarlet.azure_take_out_kt.service.EmployeeService
import moe.scarlet.azure_take_out_kt.util.JwtUtil
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/employee")
class EmployeeController(
    private val employeeService: EmployeeService,
    private val jwtProperties: JwtProperties
) {

    @Operation(summary = "员工登录")
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

    @Operation(summary = "员工登出")
    @PostMapping("/logout")
    fun logout(): JsonResult<Nothing> = JsonResult.success()

    @Operation(summary = "新增员工")
    @PostMapping
    fun add(@RequestBody employeeDTO: EmployeeDTO): JsonResult<Nothing> {
        logger.info("新增员工: $employeeDTO")
        employeeService.save(employeeDTO)
        return JsonResult.success()
    }

    @Operation(summary = "分页查询员工")
    @GetMapping("/page")
    fun query(employeePageQueryDTO: EmployeePageQueryDTO): JsonResult<QueryResult<Employee>> {
        logger.info("分页查询(员工): $employeePageQueryDTO")
        return JsonResult.success(employeeService.pageQuery(employeePageQueryDTO))
    }

    @Operation(summary = "设置员工状态")
    @PostMapping("/status/{status}")
    fun status(@PathVariable status: Int, id: Long): JsonResult<Nothing> {
        logger.info("员工状态设置: $status")
        employeeService.status(status, id)
        return JsonResult.success()
    }

    @Operation(summary = "按ID查询员工")
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): JsonResult<Employee> = JsonResult.success(employeeService.getById(id))

    @Operation(summary = "更新员工")
    @PutMapping
    fun update(@RequestBody employeeDTO: EmployeeDTO): JsonResult<Nothing> {
        logger.info("更新员工: $employeeDTO")
        employeeService.update(employeeDTO)
        return JsonResult.success()
    }

}