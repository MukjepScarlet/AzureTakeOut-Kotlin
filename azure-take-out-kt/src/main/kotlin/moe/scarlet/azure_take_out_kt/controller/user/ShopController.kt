package moe.scarlet.azure_take_out_kt.controller.user

import moe.scarlet.azure_take_out_kt.constant.ShopStatus
import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.util.RedisUtil
import org.springframework.web.bind.annotation.*


@RestController("userShopController")
@RequestMapping("/user/shop")
class ShopController(
    private val redisUtil: RedisUtil
) {

    @GetMapping("/status")
    fun status() = JsonResult.success((redisUtil[ShopStatus.KEY] as? String)?.toInt() ?: ShopStatus.CLOSED)

}
