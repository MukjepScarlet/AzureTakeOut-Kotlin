package moe.scarlet.azure_take_out_kt.handler

import moe.scarlet.azure_take_out_kt.exception.BaseException
import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler
    fun handler(e: BaseException): JsonResult<Nothing> {
        logger.error("发现异常: ${e.msg}")
        return JsonResult.error(e.msg)
    }
}