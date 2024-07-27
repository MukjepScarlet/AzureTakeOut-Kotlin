package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.mapper.OrderDetailMapper
import moe.scarlet.azure_take_out_kt.pojo.OrderDetail
import moe.scarlet.azure_take_out_kt.pojo.ShoppingCart
import moe.scarlet.azure_take_out_kt.service.OrderDetailService
import org.springframework.stereotype.Service

@Service
class OrderDetailServiceImpl : ServiceImpl<OrderDetailMapper, OrderDetail>(), OrderDetailService {

    override fun saveBatch(orderId: Long, shoppingCarts: Collection<ShoppingCart>) {
        super<ServiceImpl>.saveBatch(shoppingCarts.map {
            OrderDetail(0L, it.name, it.image, orderId, it.dishId, it.setmealId, it.dishFlavor, it.number, it.amount)
        })
    }

}