package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.context.CURRENT_USER_ID
import moe.scarlet.azure_take_out_kt.exception.ExceptionType
import moe.scarlet.azure_take_out_kt.mapper.OrdersMapper
import moe.scarlet.azure_take_out_kt.pojo.Orders
import moe.scarlet.azure_take_out_kt.pojo.dto.OrderSubmitDTO
import moe.scarlet.azure_take_out_kt.pojo.vo.OrderSubmitVO
import moe.scarlet.azure_take_out_kt.service.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrdersServiceImpl(
    private val userService: UserService,
    private val addressBookService: AddressBookService,
    private val shoppingCartService: ShoppingCartService,
    private val orderDetailService: OrderDetailService,
) : ServiceImpl<OrdersMapper, Orders>(), OrdersService {

    private val number: String
        get() = "$CURRENT_USER_ID@${System.currentTimeMillis()}"

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
            userService.currentUserName!!, addressBook.consignee, null, null, null,
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

}