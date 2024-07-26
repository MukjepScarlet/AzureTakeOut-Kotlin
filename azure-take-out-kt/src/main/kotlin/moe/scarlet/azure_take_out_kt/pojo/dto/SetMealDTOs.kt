package moe.scarlet.azure_take_out_kt.pojo.dto

import java.io.Serializable
import java.math.BigDecimal

data class SetMealDTO(
    val categoryId: Long,
    val description: String,
    val id: Long?,
    val image: String,
    val name: String,
    val price: BigDecimal,
    val setmealDishes: List<SetMealDishDTO>?,
    val status: Int,
) : Serializable

data class SetMealPageQueryDTO(
    val categoryId: Long?,
    val name: String?,
    val page: Long,
    val pageSize: Long,
    val status: Int?,
) : Serializable

data class SetMealDishDTO(
    val copies: Int,
    val dishId: Long,
    val id: Long?,
    val name: String,
    val price: BigDecimal,
    val setmealId: Long,
) : Serializable
