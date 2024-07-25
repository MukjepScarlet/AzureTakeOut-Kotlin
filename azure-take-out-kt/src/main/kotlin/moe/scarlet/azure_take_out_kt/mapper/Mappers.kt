package moe.scarlet.azure_take_out_kt.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import moe.scarlet.azure_take_out_kt.pojo.*
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

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
interface OrderDetailMapper : BaseMapper<OrderDetail>

@Mapper
interface OrdersMapper : BaseMapper<Orders>

@Mapper
interface SetMealMapper : BaseMapper<SetMeal>

@Mapper
interface SetMealDishMapper : BaseMapper<SetMealDish>

@Mapper
interface ShoppingCartMapper : BaseMapper<ShoppingCart>

@Mapper
interface UserMapper : BaseMapper<User>
