package moe.scarlet.azure_take_out_kt.service

import com.baomidou.mybatisplus.extension.service.IService
import moe.scarlet.azure_take_out_kt.pojo.*
import moe.scarlet.azure_take_out_kt.pojo.dto.*

interface CategoryService : IService<Category> {
    fun pageQuery(categoryPageQueryDTO: CategoryPageQueryDTO): QueryResult<Category>
    fun status(status: Int, id: Long)
    fun save(categoryDTO: CategoryDTO)
    fun update(categoryDTO: CategoryDTO)
    fun removeById(id: Long)
}

interface DishService : IService<Dish> {
    fun countByCategoryId(categoryId: Long): Long
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
}
