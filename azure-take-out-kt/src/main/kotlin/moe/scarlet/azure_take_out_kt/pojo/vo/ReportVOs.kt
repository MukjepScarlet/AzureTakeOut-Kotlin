package moe.scarlet.azure_take_out_kt.pojo.vo

import java.io.Serializable

data class TurnoverReportVO(
    val dateList: String,
    val turnoverList: String,
) : Serializable

data class UserReportVO(
    val dateList: String,
    val totalUserList: String,
    val newUserList: String,
) : Serializable

data class OrdersReportVO(
    val dateList: String,
    val orderCompletionRate: Double,
    val totalOrderCount: Long,
    val orderCountList: String,
    val validOrderCount: Long,
    val validOrderCountList: String,
) : Serializable

data class Top10VO(
    val nameList: String,
    val numberList: String,
) : Serializable
