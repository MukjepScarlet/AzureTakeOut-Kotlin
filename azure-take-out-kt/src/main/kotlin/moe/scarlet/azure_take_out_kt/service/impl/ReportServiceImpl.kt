package moe.scarlet.azure_take_out_kt.service.impl

import moe.scarlet.azure_take_out_kt.mapper.OrderDetailMapper
import moe.scarlet.azure_take_out_kt.mapper.OrdersMapper
import moe.scarlet.azure_take_out_kt.mapper.UserMapper
import moe.scarlet.azure_take_out_kt.pojo.Orders
import moe.scarlet.azure_take_out_kt.pojo.vo.OrdersReportVO
import moe.scarlet.azure_take_out_kt.pojo.vo.Top10VO
import moe.scarlet.azure_take_out_kt.pojo.vo.TurnoverReportVO
import moe.scarlet.azure_take_out_kt.pojo.vo.UserReportVO
import moe.scarlet.azure_take_out_kt.service.ReportService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReportServiceImpl(
    private val ordersMapper: OrdersMapper,
    private val userMapper: UserMapper,
    private val orderDetailMapper: OrderDetailMapper,
) : ReportService {

    override fun turnoverStatistics(begin: LocalDate, end: LocalDate): TurnoverReportVO {
        val turnoverStatistics = ordersMapper.turnoverStatistics(begin, end, Orders.Status.COMPLETED)
        return TurnoverReportVO(
            turnoverStatistics.joinToString(",") { it.orderDate.toString() },
            turnoverStatistics.joinToString(",") { it.totalAmount.toString() }
        )
    }

    override fun userStatistics(begin: LocalDate, end: LocalDate): UserReportVO {
        val userStatistics = userMapper.userStatistics(begin, end)
        return UserReportVO(
            userStatistics.joinToString(",") { it.date.toString() },
            userStatistics.joinToString(",") { it.totalUsers.toString() },
            userStatistics.joinToString(",") { it.registeredUsers.toString() }
        )
    }

    override fun ordersStatistics(begin: LocalDate, end: LocalDate): OrdersReportVO {
        val ordersStatistics = ordersMapper.ordersStatistics(begin, end)
        val totalOrderCount = ordersStatistics.sumOf { it.orderCount }
        val validOrderCount = ordersStatistics.sumOf { it.validOrderCount }
        return OrdersReportVO(
            ordersStatistics.joinToString(",") { it.date.toString() },
            validOrderCount.toDouble() / totalOrderCount.toDouble(),
            totalOrderCount,
            ordersStatistics.joinToString(",") { it.orderCount.toString() },
            validOrderCount,
            ordersStatistics.joinToString(",") { it.validOrderCount.toString() }
        )
    }

    override fun top10(begin: LocalDate, end: LocalDate): Top10VO {
        val top10 = orderDetailMapper.top10(begin, end)
        return Top10VO(
            top10.joinToString(",") { it.name },
            top10.joinToString(",") { it.number.toString() }
        )
    }

}
