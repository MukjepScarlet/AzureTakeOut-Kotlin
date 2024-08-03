package moe.scarlet.azure_take_out_kt.controller.admin

import io.swagger.v3.oas.annotations.Operation
import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.service.ReportService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/admin/report")
class ReportController(
    private val reportService: ReportService
) {

    @Operation(summary = "查询营业额统计")
    @GetMapping("/turnoverStatistics")
    fun turnoverStatistics(begin: LocalDate, end: LocalDate) = JsonResult.success(reportService.turnoverStatistics(begin, end))

    @Operation(summary = "查询用户统计")
    @GetMapping("/userStatistics")
    fun userStatistics(begin: LocalDate, end: LocalDate) = JsonResult.success(reportService.userStatistics(begin, end))

    @Operation(summary = "查询订单统计")
    @GetMapping("/ordersStatistics")
    fun ordersStatistics(begin: LocalDate, end: LocalDate) = JsonResult.success(reportService.ordersStatistics(begin, end))

    @Operation(summary = "查询前10热门菜品")
    @GetMapping("/top10")
    fun top10(begin: LocalDate, end: LocalDate) = JsonResult.success(reportService.top10(begin, end))

}