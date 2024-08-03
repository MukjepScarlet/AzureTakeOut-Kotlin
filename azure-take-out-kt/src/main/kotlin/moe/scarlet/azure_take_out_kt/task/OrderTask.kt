package moe.scarlet.azure_take_out_kt.task

import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.pojo.Orders
import moe.scarlet.azure_take_out_kt.service.OrdersService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class OrderTask(private val ordersService: OrdersService) {

    /**
     * 处理超时订单 / 每分钟
     */
    @Scheduled(cron = "0 * * * * ?")
    fun checkTimeout() {
        logger.info("开始处理超时订单...")
        ordersService.updateBatchById(
            ordersService.listByStatusAndOrderTimeLt(
                Orders.Status.PENDING_PAYMENT,
                LocalDateTime.now().minusMinutes(15L)
            ).map {
                it.copy(status = Orders.Status.CANCELLED, cancelReason = "超时未支付", cancelTime = LocalDateTime.now())
            }
        )
    }

    /**
     * 处理一直派送中的订单 / 每天1点
     */
    @Scheduled(cron = "0 0 1 * * ?")
    fun checkDelivery() {
        logger.info("开始清理派送中订单...")
        ordersService.updateBatchById(
            ordersService.listByStatusAndOrderTimeLt(
                Orders.Status.DELIVERY_IN_PROGRESS,
                LocalDateTime.now().minusHours(1L)
            ).map {
                it.copy(status = Orders.Status.COMPLETED, cancelTime = LocalDateTime.now())
            }
        )
    }

}