package moe.scarlet.azure_take_out_kt.pojo.dto

import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDate

data class TurnoverReportDTO(
    val orderDate: LocalDate,
    val totalAmount: BigDecimal
) : Serializable

data class UserReportDTO(
    val date: LocalDate,
    val totalUsers: Long,
    val registeredUsers: Long,
) : Serializable

data class OrdersReportDTO(
    val date: LocalDate,
    val orderCount: Long,
    val validOrderCount: Long,
) : Serializable

data class Top10DTO(
    val name: String,
    val number: Long,
) : Serializable
