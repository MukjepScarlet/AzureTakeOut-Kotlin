package moe.scarlet.azure_take_out_kt.pojo.vo

import moe.scarlet.azure_take_out_kt.pojo.OrderDetail
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderWithDetailsVO(
    val id: Long,
    val number: String?,
    val status: Int = 1,
    val userId: Long,
    val addressBookId: Long,
    val orderTime: LocalDateTime,
    val checkoutTime: LocalDateTime?,
    val payMethod: Int = 1,
    val payStatus: Byte = 0,
    val amount: BigDecimal,
    val remark: String?,
    val phone: String,
    val address: String?,
    val userName: String?,
    val consignee: String?,
    val cancelReason: String?,
    val rejectionReason: String?,
    val cancelTime: LocalDateTime?,
    val estimatedDeliveryTime: LocalDateTime?,
    val deliveryStatus: Byte = 1,
    val deliveryTime: LocalDateTime?,
    val packAmount: Int?,
    val tablewareNumber: Int?,
    val tablewareStatus: Byte = 1,
    val orderDetailList: List<OrderDetail>
) : Serializable

data class OrderSubmitVO(
    val id: Long,
    val orderTime: LocalDateTime,
    val orderNumber: String,
    val orderAmount: BigDecimal,
) : Serializable

data class OrderPayVO(
    val nonceStr: String,
    val paySign: String,
    val timeStamp: String,
    val signType: String,
    val packageStr: String,
) : Serializable
