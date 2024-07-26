package moe.scarlet.azure_take_out_kt.controller.admin

import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.util.RedisUtil
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/admin/shop")
class ShopController(
    private val redisUtil: RedisUtil
) {

    @GetMapping("/status")
    fun status() = JsonResult.success((redisUtil[Status.KEY] as? String)?.toInt() ?: Status.CLOSED)

    @PutMapping("/{status}")
    fun status(@PathVariable status: Int): JsonResult<Nothing> {
        redisUtil[Status.KEY] = status
        return JsonResult.success()
    }

    private object Status {
        const val KEY = "AZURE@SHOP_STATUS"

        const val CLOSED = 0
        const val IN_BUSINESS = 1
    }

}
