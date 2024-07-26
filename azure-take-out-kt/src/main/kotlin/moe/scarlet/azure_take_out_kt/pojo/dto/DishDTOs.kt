package moe.scarlet.azure_take_out_kt.pojo.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable
import java.math.BigDecimal

@Schema(description = "新增/修改菜品参数")
data class DishDTO(
    val id: Long?,
    val name: String,
    val categoryId: Long,
    val price: BigDecimal,
    val image: String,
    val status: Int,
    val description: String,
    val flavors: List<DishFlavorDTO>?,
) : Serializable

@Schema(description = "菜品分页查询参数")
data class DishPageQueryDTO(
    val categoryId: Long?,
    val name: String?,
    val page: Long,
    val pageSize: Long,
    val status: Int?,
) : Serializable

@Schema(description = "新增菜品口味参数")
data class DishFlavorDTO(
    val id: Long?,
    val dishId: Long?,
    val name: String,
    val value: String,
) : Serializable
