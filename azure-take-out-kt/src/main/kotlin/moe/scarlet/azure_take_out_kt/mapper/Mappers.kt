package moe.scarlet.azure_take_out_kt.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import moe.scarlet.azure_take_out_kt.pojo.*
import moe.scarlet.azure_take_out_kt.pojo.dto.*
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import java.time.LocalDate
import java.time.LocalDateTime

@Mapper
interface AddressBookMapper : BaseMapper<AddressBook>

@Mapper
interface CategoryMapper : BaseMapper<Category> {
    @Select("SELECT (name) FROM category WHERE id = #{id}")
    fun getNameById(id: Long): String
}

@Mapper
interface DishMapper : BaseMapper<Dish>

@Mapper
interface DishFlavorMapper : BaseMapper<DishFlavor>

@Mapper
interface EmployeeMapper : BaseMapper<Employee>

@Mapper
interface OrderDetailMapper : BaseMapper<OrderDetail> {
    @Select(
        """
        SELECT d.name, SUM(d.number) AS number
        FROM order_detail d
        JOIN orders o ON d.order_id = o.id
        WHERE o.status = 5
        AND o.order_time BETWEEN #{begin} AND #{end}
        GROUP BY d.name
        ORDER BY number DESC
        LIMIT 10
        """
    )
    fun top10(
        begin: LocalDate,
        end: LocalDate
    ): List<Top10DTO>
}

@Mapper
interface OrdersMapper : BaseMapper<Orders> {
    @Select(
        """
        SELECT
        COUNT(*) as total_orders_count,
        COUNT(CASE WHEN status = 5 THEN 1 END) as valid_orders_count,
        IFNULL(SUM(amount), 0) as turnover
        FROM orders o
        WHERE order_time BETWEEN #{begin} AND #{end}
        """
    )
    fun orderBusinessData(
        begin: LocalDateTime,
        end: LocalDateTime,
    ): OrdersBusinessDataDTO

    @Select(
        """
        SELECT DATE(order_time) AS order_date, SUM(amount) AS total_amount
        FROM orders
        WHERE status = #{status}
        AND order_time BETWEEN #{begin} AND #{end}
        GROUP BY DATE(order_time)
        """
    )
    fun turnoverStatistics(
        begin: LocalDate,
        end: LocalDate,
        status: Int
    ): List<TurnoverReportDTO>

    @Select(
        """
        SELECT 
            DATE(order_time) AS date,
            COUNT(*) AS orderCount,
            SUM(CASE WHEN status = 5 THEN 1 ELSE 0 END) AS validOrderCount
        FROM orders
        WHERE order_time BETWEEN #{begin} AND #{end}
        GROUP BY DATE(order_time)
        """
    )
    fun ordersStatistics(
        begin: LocalDate,
        end: LocalDate
    ): List<OrdersReportDTO>
}

@Mapper
interface SetMealMapper : BaseMapper<SetMeal>

@Mapper
interface SetMealDishMapper : BaseMapper<SetMealDish>

@Mapper
interface ShoppingCartMapper : BaseMapper<ShoppingCart>

@Mapper
interface UserMapper : BaseMapper<User> {
    @Select(
        """
        SELECT 
            DATE(create_time) AS date,
            (SELECT COUNT(*) FROM user WHERE create_time <= DATE(CONCAT(DATE(#{end}), ' 23:59:59'))) AS totalUsers,
            COUNT(*) AS registeredUsers
        FROM user
        WHERE create_time BETWEEN #{begin} AND #{end}
        GROUP BY DATE(create_time)
        """
    )
    fun userStatistics(begin: LocalDate, end: LocalDate): List<UserReportDTO>
}
