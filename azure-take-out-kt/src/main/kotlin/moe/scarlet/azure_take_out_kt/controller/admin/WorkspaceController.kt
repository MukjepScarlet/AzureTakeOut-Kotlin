package moe.scarlet.azure_take_out_kt.controller.admin

import io.swagger.v3.oas.annotations.Operation
import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.service.WorkspaceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/workspace")
class WorkspaceController(
    private val workspaceService: WorkspaceService
) {

    @Operation(summary = "查询今日运营数据")
    @GetMapping("/businessData")
    fun businessData() = JsonResult.success(workspaceService.businessData())

    @Operation(summary = "查询套餐总览")
    @GetMapping("/overviewSetmeals")
    fun overviewSetmeals() = JsonResult.success(workspaceService.overviewSetmeals())

    @Operation(summary = "查询菜品总览")
    @GetMapping("/overviewDishes")
    fun overviewDishes() = JsonResult.success(workspaceService.overviewDishes())

    @Operation(summary = "查询订单管理数据")
    @GetMapping("overviewOrders")
    fun overviewOrders() = JsonResult.success(workspaceService.overviewOrders())

}