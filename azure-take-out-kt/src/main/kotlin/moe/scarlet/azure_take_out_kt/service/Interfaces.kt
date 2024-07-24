package moe.scarlet.azure_take_out_kt.service

import com.baomidou.mybatisplus.extension.service.IService
import moe.scarlet.azure_take_out_kt.pojo.*
import moe.scarlet.azure_take_out_kt.pojo.dto.EmployeeDTO
import moe.scarlet.azure_take_out_kt.pojo.dto.EmployeeEditPasswordDTO
import moe.scarlet.azure_take_out_kt.pojo.dto.EmployeeLoginDTO
import moe.scarlet.azure_take_out_kt.pojo.dto.EmployeePageQueryDTO

interface EmployeeService : IService<Employee> {
    fun getByUsername(username: String): Employee?
    fun login(employeeLoginDTO: EmployeeLoginDTO): Employee
    fun save(employeeDTO: EmployeeDTO)
    fun pageQuery(employeePageQueryDTO: EmployeePageQueryDTO): QueryResult<Employee>
    fun status(status: Int, id: Long)
    fun update(employeeDTO: EmployeeDTO)
    fun editPassword(employeeEditPasswordDTO: EmployeeEditPasswordDTO)
}

interface CategoryService : IService<Category> {

}
