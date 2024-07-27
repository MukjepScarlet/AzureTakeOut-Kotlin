package moe.scarlet.azure_take_out_kt.pojo.dto

import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderHistoryQueryDTO(
    val page: Long,
    val pageSize: Long,
    val status: Int?
) : Serializable

data class OrderSubmitDTO(
    val addressBookId: Long,
    val payMethod: Int,
    val amount: BigDecimal,
    val remark: String,
    val estimatedDeliveryTime: LocalDateTime,
    val deliveryStatus: Byte,
    val packAmount: Int,
    val tablewareNumber: Int,
    val tablewareStatus: Byte,
) : Serializable

data class OrderPayDTO(
    val orderNumber: String,
    val payMethod: Int,
) : Serializable
