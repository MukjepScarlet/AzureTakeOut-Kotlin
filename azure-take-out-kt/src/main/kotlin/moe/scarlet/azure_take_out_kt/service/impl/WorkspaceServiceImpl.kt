package moe.scarlet.azure_take_out_kt.service.impl

import moe.scarlet.azure_take_out_kt.constant.StatusConstant
import moe.scarlet.azure_take_out_kt.extension.buildQueryWrapper
import moe.scarlet.azure_take_out_kt.extension.selectCount
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

    override fun businessData(begin: LocalDateTime, end: LocalDateTime): BusinessDataVO {
        val newUsers = userMapper.selectCount {
            User::createTime ge begin
            User::createTime le end
        }

        val (totalOrdersCount, validOrdersCount, turnover) = ordersMapper.orderBusinessData(begin, end)

        val orderCompletionRate = if (totalOrdersCount != 0L) validOrdersCount.toDouble() / totalOrdersCount else 0.0
        val unitPrice = if (validOrdersCount != 0L) turnover.toDouble() / validOrdersCount else 0.0

        return BusinessDataVO(newUsers, orderCompletionRate, turnover.toDouble(), unitPrice, validOrdersCount)
    }

    override fun businessData(): BusinessDataVO {
        val startOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
        val endOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MAX)

        return this.businessData(startOfToday, endOfToday)
    }

    override fun overviewSetmeals() = OverviewDishesOrSetMealsVO(
        setMealMapper.selectCount {
            SetMeal::status eq StatusConstant.DISABLE
        },
        setMealMapper.selectCount {
            SetMeal::status eq StatusConstant.ENABLE
        },
    )

    override fun overviewDishes() = OverviewDishesOrSetMealsVO(
        dishMapper.selectCount {
            Dish::status eq StatusConstant.DISABLE
        },
        dishMapper.selectCount {
            Dish::status eq StatusConstant.ENABLE
        }
    )

    override fun overviewOrders(): OverviewOrdersVO {
        return buildQueryWrapper<Orders> {
            Orders::orderTime ge LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
            Orders::orderTime le LocalDateTime.of(LocalDate.now(), LocalTime.MAX)
        }.let {
            OverviewOrdersVO(
                ordersMapper.selectCount(it),
                ordersMapper.selectCount(it) { Orders::status eq Orders.Status.CANCELLED },
                ordersMapper.selectCount(it) { Orders::status eq Orders.Status.COMPLETED },
                ordersMapper.selectCount(it) { Orders::status eq Orders.Status.CONFIRMED },
                ordersMapper.selectCount(it) { Orders::status eq Orders.Status.TO_BE_CONFIRMED },
            )
        }
    }

}