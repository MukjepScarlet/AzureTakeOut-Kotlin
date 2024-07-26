package moe.scarlet.azure_take_out_kt.pojo.vo

import java.math.BigDecimal

data class BusinessDataVO(
    val newUsers: Long,
    val orderCompletionRate: Double,
    val turnover: Double,
    val unitPrice: Double,
    val validOrderCount: Long,
)

data class OverviewDishesOrSetMealsVO(
    val discontinued: Long,
    val sold: Long,
)

data class OverviewOrdersVO(
    val allOrders: Long,
    val cancelledOrders: Long,
    val completedOrders: Long,
    val deliveredOrders: Long,
    val waitingOrders: Long,
)
