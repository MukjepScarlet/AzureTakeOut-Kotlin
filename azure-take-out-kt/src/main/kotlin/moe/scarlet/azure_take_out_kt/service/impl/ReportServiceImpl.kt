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
import moe.scarlet.azure_take_out_kt.service.WorkspaceService
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class ReportServiceImpl(
    private val ordersMapper: OrdersMapper,
    private val userMapper: UserMapper,
    private val orderDetailMapper: OrderDetailMapper,
    private val workspaceService: WorkspaceService,
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

    override fun export(): ByteArray {
        // 获取最近30天的营业数据
        val end = LocalDate.now()
        val begin = end.minusDays(30)
        val (newUsers, orderCompletionRate, turnover, unitPrice, validOrderCount) = workspaceService.businessData(
            LocalDateTime.of(begin, LocalTime.MIN),
            LocalDateTime.of(end, LocalTime.MIN)
        )

        // 导出到XLSX文件
        return XSSFWorkbook(ClassPathResource("/static/运营数据报表模板.xlsx").inputStream).use {
            it.getSheet("Sheet1").apply {
                getRow(1).getCell(1).setCellValue("时间: $begin 到 $end")
                // 概览数据
                getRow(3).apply {
                    getCell(2).setCellValue(turnover)
                    getCell(4).setCellValue(orderCompletionRate)
                    getCell(6).setCellValue(newUsers.toDouble())
                }
                getRow(4).apply {
                    getCell(2).setCellValue(validOrderCount.toDouble())
                    getCell(4).setCellValue(unitPrice)
                }
                // 明细数据
                for (i in 0..<30) {
                    val date = begin.plusDays(i.toLong())
                    // 大概用group by更好 但是我不想写了
                    val current = workspaceService.businessData(LocalDateTime.of(date, LocalTime.MIN),
                        LocalDateTime.of(date, LocalTime.MAX))
                    getRow(i + 7).apply {
                        getCell(1).setCellValue(date)
                        getCell(2).setCellValue(current.turnover)
                        getCell(3).setCellValue(current.validOrderCount.toDouble())
                        getCell(4).setCellValue(current.orderCompletionRate)
                        getCell(5).setCellValue(current.unitPrice)
                        getCell(6).setCellValue(current.newUsers.toDouble())
                    }
                }
            }
            // 输出byte[]
            ByteArrayOutputStream().use { o ->
                it.write(o)
                o.toByteArray()
            }
        }
    }

}
