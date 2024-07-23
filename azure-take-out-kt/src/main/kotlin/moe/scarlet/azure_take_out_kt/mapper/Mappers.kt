package moe.scarlet.azure_take_out_kt.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import moe.scarlet.azure_take_out_kt.pojo.Employee
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface EmployeeMapper : BaseMapper<Employee> {
    @Select("select * from employee where username = #{username}")
    fun getByUsername(username: String): Employee?
}