package moe.scarlet.azure_take_out_kt.controller.user

import io.swagger.v3.oas.annotations.Operation
import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.mapper.OrdersMapper
import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.pojo.QueryResult
import moe.scarlet.azure_take_out_kt.pojo.dto.OrderHistoryQueryDTO
import moe.scarlet.azure_take_out_kt.pojo.dto.OrderPayDTO
import moe.scarlet.azure_take_out_kt.pojo.dto.OrderSubmitDTO
import moe.scarlet.azure_take_out_kt.pojo.vo.OrderPayVO
import moe.scarlet.azure_take_out_kt.pojo.vo.OrderSubmitVO
import moe.scarlet.azure_take_out_kt.pojo.vo.OrderWithDetailsVO
import moe.scarlet.azure_take_out_kt.service.OrdersService
import org.springframework.web.bind.annotation.*

@RestController("userOrderController")
@RequestMapping("/user/order")
class OrderController(
    private val ordersService: OrdersService,
) {

//    @Operation(summary = "催单")
//    @GetMapping("/reminder/{id}")
//    fun reminder(@PathVariable id: Long): JsonResult<Nothing> {
//        logger.info("催单: $id")
//        // TODO
//        return JsonResult.success()
//    }
//
//    @Operation(summary = "再来一单")
//    @PostMapping("/repetition/{id}")
//    fun repetition(@PathVariable id: Long): JsonResult<Nothing> {
//        logger.info("再来一单: $id")
//        // TODO
//        return JsonResult.success()
//    }

    @Operation(summary = "历史订单查询")
    @GetMapping("/historyOrders")
    fun history(orderHistoryQueryDTO: OrderHistoryQueryDTO): JsonResult<QueryResult<OrderWithDetailsVO>> {
        logger.info("历史订单查询: $orderHistoryQueryDTO")
        return JsonResult.success(ordersService.history(orderHistoryQueryDTO))
    }
//
//    @Operation(summary = "取消订单")
//    @PutMapping("/cancel/{id}")
//    fun cancel(@PathVariable id: Long): JsonResult<Nothing> {
//        logger.info("取消订单: $id")
//        // TODO
//        return JsonResult.success()
//    }
//
//    @Operation(summary = "查询订单详情")
//    @GetMapping("/orderDetail/{id}")
//    fun detail(@PathVariable id: Long): JsonResult<OrderWithDetailsVO> {
//        logger.info("查询订单详情: $id")
//        // TODO
//    }

    @Operation(summary = "下单")
    @PostMapping("/submit")
    fun submit(@RequestBody orderSubmitDTO: OrderSubmitDTO): JsonResult<OrderSubmitVO> {
        logger.info("下单: $orderSubmitDTO")
        return JsonResult.success(ordersService.submit(orderSubmitDTO))
    }

//    @Operation(summary = "订单支付")
//    @PutMapping("/payment")
//    fun pay(@RequestBody orderPayDTO: OrderPayDTO): JsonResult<OrderPayVO> {
//        logger.info("订单支付: $orderPayDTO")
//        // TODO
//    }

}