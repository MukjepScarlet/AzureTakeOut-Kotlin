package moe.scarlet.azure_take_out_kt.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import moe.scarlet.azure_take_out_kt.constant.JwtClaimsConstant
import moe.scarlet.azure_take_out_kt.context.CURRENT_EMPLOYEE_ID
import moe.scarlet.azure_take_out_kt.context.CURRENT_USER_ID
import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.property.JwtProperties
import moe.scarlet.azure_take_out_kt.util.JwtUtil
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Configuration
class JwtTokenAdminInterceptor(
    private val jwtProperties: JwtProperties
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler !is HandlerMethod) return true

        val token = request.getHeader(jwtProperties.adminTokenName)

        return try {
            logger.info("JWT令牌: $token")
            val claims = JwtUtil.parseJWT(jwtProperties.adminSecretKey, token)
            val employeeId = claims[JwtClaimsConstant.EMP_ID].toString().toLong()
            logger.info("解析的员工ID: $employeeId")
            CURRENT_EMPLOYEE_ID = employeeId
            true
        } catch (e: Exception) {
            response.status = 401
            false
        }
    }

}

@Configuration
class JwtTokenUserInterceptor(
    private val jwtProperties: JwtProperties
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler !is HandlerMethod) return true

        val token = request.getHeader(jwtProperties.userTokenName)

        return try {
            logger.info("JWT令牌: $token")
            val claims = JwtUtil.parseJWT(jwtProperties.userSecretKey, token)
            val userId = claims[JwtClaimsConstant.USER_ID].toString().toLong()
            logger.info("解析的用户ID: $userId")
            CURRENT_USER_ID = userId
            true
        } catch (e: Exception) {
            response.status = 401
            false
        }
    }

}
