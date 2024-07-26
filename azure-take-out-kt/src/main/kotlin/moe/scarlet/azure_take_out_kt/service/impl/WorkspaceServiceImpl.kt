package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import moe.scarlet.azure_take_out_kt.constant.StatusConstant
import moe.scarlet.azure_take_out_kt.mapper.DishMapper
import moe.scarlet.azure_take_out_kt.mapper.OrdersMapper
import moe.scarlet.azure_take_out_kt.mapper.SetMealMapper
import moe.scarlet.azure_take_out_kt.mapper.UserMapper
import moe.scarlet.azure_take_out_kt.pojo.Dish
import moe.scarlet.azure_take_out_kt.pojo.Orders
import moe.scarlet.azure_take_out_kt.pojo.SetMeal
import moe.scarlet.azure_take_out_kt.pojo.User
import moe.scarlet.azure_take_out_kt.pojo.vo.BusinessDataVO
import moe.scarlet.azure_take_out_kt.pojo.vo.OverviewDishesOrSetMealsVO
import moe.scarlet.azure_take_out_kt.pojo.vo.OverviewOrdersVO
import moe.scarlet.azure_take_out_kt.service.WorkspaceService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


@Service
class WorkspaceServiceImpl(
    private val userMapper: UserMapper,
    private val dishMapper: DishMapper,
    private val setMealMapper: SetMealMapper,
    private val ordersMapper: OrdersMapper
) : WorkspaceService {

    override fun businessData(): BusinessDataVO {
        val startOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
        val endOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MAX)

        val newUsers =
            userMapper.selectCount(KtQueryWrapper(User::class.java).between(User::createTime, startOfToday, endOfToday))

        return KtQueryWrapper(Orders::class.java).between(Orders::orderTime, startOfToday, endOfToday).let {
            val totalOrdersCount = ordersMapper.selectCount(it)
            val validOrderCount = ordersMapper.selectCount(it.eq(Orders::status, Orders.Status.COMPLETED))
            val turnover = ordersMapper.selectObjs<Double>(it.select(Orders::amount)).sum()

            val orderCompletionRate = if (totalOrdersCount != 0L) validOrderCount.toDouble() / totalOrdersCount else 0.0
            val unitPrice = if (validOrderCount != 0L) turnover / validOrderCount else 0.0

            BusinessDataVO(newUsers, orderCompletionRate, turnover, unitPrice, validOrderCount)
        }
    }

    override fun overviewSetmeals() = OverviewDishesOrSetMealsVO(
        setMealMapper.selectCount(KtQueryWrapper(SetMeal::class.java).eq(SetMeal::status, StatusConstant.DISABLE)),
        setMealMapper.selectCount(KtQueryWrapper(SetMeal::class.java).eq(SetMeal::status, StatusConstant.ENABLE)),
    )

    override fun overviewDishes() = OverviewDishesOrSetMealsVO(
        dishMapper.selectCount(KtQueryWrapper(Dish::class.java).eq(Dish::status, StatusConstant.DISABLE)),
        dishMapper.selectCount(KtQueryWrapper(Dish::class.java).eq(Dish::status, StatusConstant.ENABLE)),
    )

    override fun overviewOrders(): OverviewOrdersVO {
        val startOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
        val endOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MAX)

        return KtQueryWrapper(Orders::class.java).between(Orders::orderTime, startOfToday, endOfToday).let {
            OverviewOrdersVO(
                ordersMapper.selectCount(it),
                ordersMapper.selectCount(it.eq(Orders::status, Orders.Status.CANCELLED)),
                ordersMapper.selectCount(it.eq(Orders::status, Orders.Status.COMPLETED)),
                ordersMapper.selectCount(it.eq(Orders::status, Orders.Status.CONFIRMED)),
                ordersMapper.selectCount(it.eq(Orders::status, Orders.Status.TO_BE_CONFIRMED)),
            )
        }
    }

}