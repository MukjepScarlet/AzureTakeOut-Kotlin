package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.context.CURRENT_USER_ID
import moe.scarlet.azure_take_out_kt.exception.ExceptionType
import moe.scarlet.azure_take_out_kt.handler.MyWebSocketHandler
import moe.scarlet.azure_take_out_kt.mapper.OrdersMapper
import moe.scarlet.azure_take_out_kt.pojo.OrderDetail
import moe.scarlet.azure_take_out_kt.pojo.Orders
import moe.scarlet.azure_take_out_kt.pojo.QueryResult
import moe.scarlet.azure_take_out_kt.pojo.ShoppingCart
import moe.scarlet.azure_take_out_kt.pojo.dto.OrderHistoryQueryDTO
import moe.scarlet.azure_take_out_kt.pojo.dto.OrderPayDTO
import moe.scarlet.azure_take_out_kt.pojo.dto.OrderSubmitDTO
import moe.scarlet.azure_take_out_kt.pojo.vo.OrderPayVO
import moe.scarlet.azure_take_out_kt.pojo.vo.OrderSubmitVO
import moe.scarlet.azure_take_out_kt.pojo.vo.OrderWithDetailsVO
import moe.scarlet.azure_take_out_kt.service.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrdersServiceImpl(
    private val ordersMapper: OrdersMapper,
    private val userService: UserService,
    private val addressBookService: AddressBookService,
    private val shoppingCartService: ShoppingCartService,
    private val orderDetailService: OrderDetailService,
    private val webSocketHandler: MyWebSocketHandler,
) : ServiceImpl<OrdersMapper, Orders>(), OrdersService {

    private val number: String
        get() = "$CURRENT_USER_ID@${System.currentTimeMillis()}"

    override fun reminder(id: Long) {
        webSocketHandler.reminder(this.getById(id) ?: throw ExceptionType.ORDER_NOT_FOUND.asException())
    }

    override fun repeat(id: Long) {
        shoppingCartService.saveBatch(
            orderDetailService.listByOrderId(id).map { (_, name, image, _, dishId, setmealId, dishFlavor, number1, amount) ->
                ShoppingCart(0L, name, image, CURRENT_USER_ID!!, dishId, setmealId, dishFlavor, number1, amount, null)
            }
        )
    }

    override fun cancel(id: Long) {
        TODO("Not yet implemented")
    }

    override fun pay(orderPayDTO: OrderPayDTO): OrderPayVO {
        TODO("Not yet implemented")
    }

    override fun submit(orderSubmitDTO: OrderSubmitDTO): OrderSubmitVO {
        val (addressBookId, payMethod, amount, remark, estimatedDeliveryTime,
            deliveryStatus, packAmount, tablewareNumber, tablewareStatus) = orderSubmitDTO

        // 检查地址簿是否为空
        val addressBook =
            addressBookService.getById(addressBookId) ?: throw ExceptionType.ADDRESS_BOOK_IS_NULL.asException()

        // 检查购物车是否为空
        val shoppingCarts = shoppingCartService.listByCurrentUser()
        if (shoppingCarts.isEmpty())
            throw ExceptionType.SHOPPING_CART_IS_NULL.asException()

        // 插入订单表
        val orders = Orders(
            0L, this.number, Orders.Status.PENDING_PAYMENT,
            CURRENT_USER_ID!!, addressBookId, LocalDateTime.now(), null, payMethod,
            Orders.PayStatus.PAID,
//            Orders.PayStatus.UN_PAID,
            amount, remark, addressBook.phone, addressBook.address,
            userService.currentUserName ?: "", addressBook.consignee, null, null, null,
            estimatedDeliveryTime, deliveryStatus, null, packAmount, tablewareNumber, tablewareStatus
        )
        this.save(orders)

        // 插入订单详情表 (购物车)
        orderDetailService.saveBatch(orders.id, shoppingCarts)

        // 清空购物车
        shoppingCartService.cleanByCurrentUser()

        // 返回VO
        return OrderSubmitVO(orders.id, orders.orderTime, orders.number, orders.amount)
    }

    override fun history(orderHistoryQueryDTO: OrderHistoryQueryDTO): QueryResult<OrderWithDetailsVO> {
        val (page, pageSize, status) = orderHistoryQueryDTO
        val result = ordersMapper.selectPage(
            Page(page, pageSize),
            KtQueryWrapper(Orders::class.java)
                .eq(status != null, Orders::status, status)
                .eq(Orders::userId, CURRENT_USER_ID ?: throw ExceptionType.USER_NOT_LOGIN.asException())
        )
        return QueryResult(
            result.total,
            result.records.map {
                it.toOrderWithDetailsVO(orderDetailService.listByOrderId(it.id))
            }
        )
    }

    override fun getByIdWithDetails(id: Long): OrderWithDetailsVO {
        return this.getById(id).toOrderWithDetailsVO(orderDetailService.listByOrderId(id))
    }

    private fun Orders.toOrderWithDetailsVO(orderDetailList: List<OrderDetail>) = OrderWithDetailsVO(
        id = this.id,
        number = this.number,
        status = this.status,
        userId = this.userId,
        addressBookId = this.addressBookId,
        orderTime = this.orderTime,
        checkoutTime = this.checkoutTime,
        payMethod = this.payMethod,
        payStatus = this.payStatus,
        amount = this.amount,
        remark = this.remark,
        phone = this.phone,
        address = this.address,
        userName = this.userName,
        consignee = this.consignee,
        cancelReason = this.cancelReason,
        rejectionReason = this.rejectionReason,
        cancelTime = this.cancelTime,
        estimatedDeliveryTime = this.estimatedDeliveryTime,
        deliveryStatus = this.deliveryStatus,
        deliveryTime = this.deliveryTime,
        packAmount = this.packAmount,
        tablewareNumber = this.tablewareNumber,
        tablewareStatus = this.tablewareStatus,
        orderDetailList = orderDetailList
    )

}