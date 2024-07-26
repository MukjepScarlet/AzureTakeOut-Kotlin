package moe.scarlet.azure_take_out_kt.pojo.vo

import moe.scarlet.azure_take_out_kt.pojo.SetMealDish
import java.math.BigDecimal
import java.time.LocalDateTime

data class SetMealVO(
    val categoryId: Long,
    val categoryName: String,
    val description: String,
    val id: Long,
    val image: String,
    val name: String,
    val price: BigDecimal,
    val status: Int,
    val updateTime: LocalDateTime,
)

data class SetMealWithDishesVO(
    val categoryId: Long,
    val categoryName: String,
    val description: String,
    val id: Long,
    val image: String,
    val name: String,
    val price: BigDecimal,
    val setmealDishes: List<SetMealDish>,
    val status: Int,
    val updateTime: LocalDateTime,
)
