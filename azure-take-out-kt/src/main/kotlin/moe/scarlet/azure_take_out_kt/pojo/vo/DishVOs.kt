package moe.scarlet.azure_take_out_kt.pojo.vo

import moe.scarlet.azure_take_out_kt.pojo.DishFlavor
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

data class DishVO(
    val id: Long,
    val name: String,
    val categoryId: Long,
    val price: BigDecimal,
    val image: String,
    val description: String,
    val status: Int,
    val updateTime: LocalDateTime,
    val categoryName: String,
) : Serializable

data class DishWithFlavorsVO(
    val categoryId: Long,
    val categoryName: String,
    val description: String,
    val flavors: List<DishFlavor>,
    val id: Long,
    val image: String,
    val name: String,
    val price: BigDecimal,
    val status: Int,
    val updateTime: LocalDateTime,
) : Serializable
