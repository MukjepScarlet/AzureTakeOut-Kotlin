package moe.scarlet.azure_take_out_kt.service

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.constant.EmployeeStatus
import moe.scarlet.azure_take_out_kt.constant.PasswordConstant
import moe.scarlet.azure_take_out_kt.context.CURRENT_EMPLOYEE_ID
import moe.scarlet.azure_take_out_kt.exception.ExceptionType
import moe.scarlet.azure_take_out_kt.extension.toMD5
import moe.scarlet.azure_take_out_kt.mapper.EmployeeMapper
import moe.scarlet.azure_take_out_kt.pojo.Employee
import moe.scarlet.azure_take_out_kt.pojo.EmployeeDTO
import moe.scarlet.azure_take_out_kt.pojo.EmployeeLoginDTO
import org.springframework.stereotype.Service
import java.time.LocalDateTime

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

    override fun save(employeeDTO: EmployeeDTO) {
        val (id, username, name, phone, sex, idNumber) = employeeDTO

        // 若新增的用户重名
        if (employeeMapper.getByUsername(username) != null)
            throw ExceptionType.USERNAME_OCCUPIED.asException()

        val employee = Employee(
            id ?: 0L,
            name,
            username,
            PasswordConstant.DEFAULT_PASSWORD.toMD5(),
            phone,
            sex,
            idNumber,
            EmployeeStatus.ENABLE,
            LocalDateTime.now(),
            LocalDateTime.now(),
            CURRENT_EMPLOYEE_ID.get(),
            CURRENT_EMPLOYEE_ID.get()
        )
        this.save(employee)
    }
}

