package moe.scarlet.azure_take_out_kt.controller.admin

import io.swagger.v3.oas.annotations.Operation
import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.service.ReportService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.LocalDate

@RestController
@RequestMapping("/admin/report")
class ReportController(
    private val reportService: ReportService
) {

    @Operation(summary = "查询营业额统计")
    @GetMapping("/turnoverStatistics")
    fun turnoverStatistics(begin: LocalDate, end: LocalDate) =
        JsonResult.success(reportService.turnoverStatistics(begin, end))

    @Operation(summary = "查询用户统计")
    @GetMapping("/userStatistics")
    fun userStatistics(begin: LocalDate, end: LocalDate) = JsonResult.success(reportService.userStatistics(begin, end))

    @Operation(summary = "查询订单统计")
    @GetMapping("/ordersStatistics")
    fun ordersStatistics(begin: LocalDate, end: LocalDate) =
        JsonResult.success(reportService.ordersStatistics(begin, end))

    @Operation(summary = "查询前10热门菜品")
    @GetMapping("/top10")
    fun top10(begin: LocalDate, end: LocalDate) = JsonResult.success(reportService.top10(begin, end))

    @Operation(summary = "导出Excel报表")
    @GetMapping("/export")
    fun export(): ResponseEntity<ByteArray> = ResponseEntity.ok()
        .headers(HttpHeaders().apply {
            contentType = MediaType.APPLICATION_OCTET_STREAM
            val fileName = URLEncoder.encode("运营数据报表.xlsx", StandardCharsets.UTF_8)
            set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${fileName}\"")
        })
        .body(reportService.export())

}