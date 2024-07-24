package moe.scarlet.azure_take_out_kt.service

import com.baomidou.mybatisplus.extension.service.IService
import moe.scarlet.azure_take_out_kt.pojo.Category
import moe.scarlet.azure_take_out_kt.pojo.Employee
import moe.scarlet.azure_take_out_kt.pojo.QueryResult
import moe.scarlet.azure_take_out_kt.pojo.dto.*

interface EmployeeService : IService<Employee> {
    fun getByUsername(username: String): Employee?
    fun login(employeeLoginDTO: EmployeeLoginDTO): Employee
    fun pageQuery(employeePageQueryDTO: EmployeePageQueryDTO): QueryResult<Employee>
    fun status(status: Int, id: Long)
    fun save(employeeDTO: EmployeeDTO)
    fun update(employeeDTO: EmployeeDTO)
    fun editPassword(employeeEditPasswordDTO: EmployeeEditPasswordDTO)
}

interface CategoryService : IService<Category> {
    fun pageQuery(categoryPageQueryDTO: CategoryPageQueryDTO): QueryResult<Category>
    fun status(status: Int, id: Long)
    fun save(categoryDTO: CategoryDTO)
    fun update(categoryDTO: CategoryDTO)
}
