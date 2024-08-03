package moe.scarlet.azure_take_out_kt.service

import com.baomidou.mybatisplus.extension.service.IService
import moe.scarlet.azure_take_out_kt.pojo.*
import moe.scarlet.azure_take_out_kt.pojo.dto.*
import moe.scarlet.azure_take_out_kt.pojo.vo.*
import java.time.LocalDateTime

interface CategoryService : IService<Category> {
    fun pageQuery(categoryPageQueryDTO: CategoryPageQueryDTO): QueryResult<Category>
    fun status(status: Int, id: Long)
    fun save(categoryDTO: CategoryDTO)
    fun update(categoryDTO: CategoryDTO)
    fun removeById(id: Long)
    fun list(type: Int?): List<Category>
}

interface DishService : IService<Dish> {
    fun countByCategoryId(categoryId: Long): Long
    fun pageQuery(dishPageQueryDTO: DishPageQueryDTO): QueryResult<DishVO>
    fun status(status: Int, id: Long)
    fun save(dishDTO: DishDTO)
    fun update(dishDTO: DishDTO)
    fun delete(idList: List<Long>)
    fun getByIdWithFlavor(id: Long): DishWithFlavorsVO
    fun list(categoryId: Long): List<Dish>
    fun listWithFlavors(categoryId: Long): List<DishWithFlavorsVO>
}

interface DishFlavorService : IService<DishFlavor> {
    fun saveBatch(dishId: Long, dishFlavorDTO: Collection<DishFlavorDTO>)
    fun listByDishId(dishId: Long): List<DishFlavor>
    fun removeByDishId(dishId: Long)
}

interface EmployeeService : IService<Employee> {
    fun getByUsername(username: String): Employee?
    fun login(employeeLoginDTO: EmployeeLoginDTO): Employee
    fun pageQuery(employeePageQueryDTO: EmployeePageQueryDTO): QueryResult<Employee>
    fun status(status: Int, id: Long)
    fun save(employeeDTO: EmployeeDTO)
    fun update(employeeDTO: EmployeeDTO)
    fun editPassword(employeeEditPasswordDTO: EmployeeEditPasswordDTO)
}

interface SetMealService : IService<SetMeal> {
    fun countByCategoryId(categoryId: Long): Long
    fun getByCategoryId(categoryId: Long): List<SetMeal>
    fun pageQuery(setMealPageQueryDTO: SetMealPageQueryDTO): QueryResult<SetMealVO>
    fun status(status: Int, id: Long)
    fun save(setMealDTO: SetMealDTO)
    fun update(setMealDTO: SetMealDTO)
    fun delete(idList: List<Long>)
    fun getByIdWithDishes(id: Long): SetMealWithDishesVO
}

interface SetMealDishService : IService<SetMealDish> {
    fun countByDishId(dishId: Long): Long
    fun countBySetMealId(setmealId: Long): Long
    fun getBySetMealId(setmealId: Long): List<SetMealDish>
    fun saveBatch(setmealId: Long, setMealDTO: Collection<SetMealDishDTO>)
    fun removeBySetMealId(setmealId: Long)
}

interface WorkspaceService {
    fun businessData(): BusinessDataVO
    fun overviewSetmeals(): OverviewDishesOrSetMealsVO
    fun overviewDishes(): OverviewDishesOrSetMealsVO
    fun overviewOrders(): OverviewOrdersVO
}

interface UserService : IService<User> {
    val currentUserName: String?
    fun getByOpenId(openid: String): User?
    suspend fun login(userDTO: UserDTO): User
}

interface ShoppingCartService : IService<ShoppingCart> {
    fun sub(shoppingCartDTO: ShoppingCartDTO)
    fun listByCurrentUser(): List<ShoppingCart>
    fun add(shoppingCartDTO: ShoppingCartDTO)
    fun cleanByCurrentUser()
}

interface AddressBookService : IService<AddressBook> {
    fun listByCurrentUser(): List<AddressBook>
    fun getDefaultByCurrentUser(): AddressBook?
    fun setDefaultByCurrentUser(addressBookDefaultDTO: AddressBookDefaultDTO)
}

interface OrdersService : IService<Orders> {
    // user
    fun reminder(id: Long)
    fun repeat(id: Long)
    fun cancel(id: Long)
    fun pay(orderPayDTO: OrderPayDTO): OrderPayVO
//    fun paySuccess(outTradeNo: String)
    fun submit(orderSubmitDTO: OrderSubmitDTO): OrderSubmitVO
    fun history(orderHistoryQueryDTO: OrderHistoryQueryDTO): QueryResult<OrderWithDetailsVO>
    fun getByIdWithDetails(id: Long): OrderWithDetailsVO
    // admin
    fun listByStatusAndOrderTimeLt(status: Int, orderTime: LocalDateTime): List<Orders>
}

interface OrderDetailService : IService<OrderDetail> {
    fun saveBatch(orderId: Long, shoppingCarts: Collection<ShoppingCart>)
    fun listByOrderId(orderId: Long): List<OrderDetail>
}
