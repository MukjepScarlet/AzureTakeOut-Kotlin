package moe.scarlet.azure_take_out_kt.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import moe.scarlet.azure_take_out_kt.pojo.Category
import moe.scarlet.azure_take_out_kt.pojo.Employee
import org.apache.ibatis.annotations.Mapper

@Mapper
interface EmployeeMapper : BaseMapper<Employee>

@Mapper
interface CategoryMapper : BaseMapper<Category>
