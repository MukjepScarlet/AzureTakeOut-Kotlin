package moe.scarlet.azure_take_out_kt.pojo.vo

import java.io.Serializable

data class BusinessDataVO(
    val newUsers: Long,
    val orderCompletionRate: Double,
    val turnover: Double,
    val unitPrice: Double,
    val validOrderCount: Long,
) : Serializable

data class OverviewDishesOrSetMealsVO(
    val discontinued: Long,
    val sold: Long,
) : Serializable

data class OverviewOrdersVO(
    val allOrders: Long,
    val cancelledOrders: Long,
    val completedOrders: Long,
    val deliveredOrders: Long,
    val waitingOrders: Long,
) : Serializable
