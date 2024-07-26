package moe.scarlet.azure_take_out_kt.pojo.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

/**
 * 注意, 只能是dish或者setmeal中的一种
 */
@Schema(description = "添加购物车参数")
data class ShoppingCartDTO(
    val dishFlavor: String?,
    val dishId: Long?,
    val setmealId: Long?,
) : Serializable


val a = 1