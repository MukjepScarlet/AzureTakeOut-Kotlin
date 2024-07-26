package moe.scarlet.azure_take_out_kt.controller.user

import io.swagger.v3.oas.annotations.Operation
import moe.scarlet.azure_take_out_kt.constant.JwtClaimsConstant
import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.pojo.dto.UserDTO
import moe.scarlet.azure_take_out_kt.pojo.vo.UserVO
import moe.scarlet.azure_take_out_kt.property.JwtProperties
import moe.scarlet.azure_take_out_kt.service.UserService
import moe.scarlet.azure_take_out_kt.util.JwtUtil
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user/user")
class UserController(
    private val userService: UserService,
    private val jwtProperties: JwtProperties
) {

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    suspend fun login(@RequestBody userDTO: UserDTO): JsonResult<UserVO> {
        logger.info("微信用户登录: $userDTO")

        val (id, openid) = userService.login(userDTO)

        // 创建JWT令牌
        val token: String = JwtUtil.createJWT(
            jwtProperties.adminSecretKey,
            jwtProperties.adminTTL,
            mapOf(JwtClaimsConstant.USER_ID to id)
        )

        return JsonResult.success(UserVO(id, openid, token))
    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    fun logout() = JsonResult.success<Nothing>()

}