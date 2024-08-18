package moe.scarlet.azure_take_out_kt.pojo.dto

import java.io.Serializable
import java.math.BigDecimal

data class OrdersBusinessDataDTO(
    val totalOrdersCount: Long,
    val validOrdersCount: Long,
    val turnover: BigDecimal,
) : Serializable
