package moe.scarlet.azure_take_out_kt.service

import com.baomidou.mybatisplus.extension.service.IService
import moe.scarlet.azure_take_out_kt.pojo.Employee
import moe.scarlet.azure_take_out_kt.pojo.EmployeeDTO
import moe.scarlet.azure_take_out_kt.pojo.EmployeeLoginDTO

interface EmployeeService : IService<Employee> {
    fun login(employeeLoginDTO: EmployeeLoginDTO): Employee
    fun save(employeeDTO: EmployeeDTO)
}

