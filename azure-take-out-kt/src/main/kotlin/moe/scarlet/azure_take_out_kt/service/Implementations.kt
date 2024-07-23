package moe.scarlet.azure_take_out_kt.service

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.constant.EmployeeStatus
import moe.scarlet.azure_take_out_kt.exception.ExceptionType
import moe.scarlet.azure_take_out_kt.extension.toMD5
import moe.scarlet.azure_take_out_kt.mapper.EmployeeMapper
import moe.scarlet.azure_take_out_kt.pojo.Employee
import moe.scarlet.azure_take_out_kt.pojo.EmployeeLoginDTO
import org.springframework.stereotype.Service

@Service
class EmployeeServiceImpl(
    private val employeeMapper: EmployeeMapper
) : ServiceImpl<EmployeeMapper, Employee>(), EmployeeService {
    override fun login(employeeLoginDTO: EmployeeLoginDTO): Employee {
        val (username, password) = employeeLoginDTO

        // 检查账号存在
        val employee = employeeMapper.getByUsername(username) ?: throw ExceptionType.ACCOUNT_NOT_FOUND.asException()

        // 检查密码
        if (password.toMD5() != employee.password)
            throw ExceptionType.PASSWORD_ERROR.asException()

        // 检查状态
        if (employee.status == EmployeeStatus.DISABLE)
            throw ExceptionType.ACCOUNT_LOCKED.asException()

        return employee
    }
}

