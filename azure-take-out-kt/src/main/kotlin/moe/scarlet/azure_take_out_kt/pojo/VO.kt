package moe.scarlet.azure_take_out_kt.pojo

import java.math.BigDecimal
import java.time.LocalDateTime

data class EmployeeLoginVO(
    val id: Long,
    val userName: String, // 这个字段和数据库里大小写对不上!!
    val name: String,
    val token: String
)

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
)

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
)
