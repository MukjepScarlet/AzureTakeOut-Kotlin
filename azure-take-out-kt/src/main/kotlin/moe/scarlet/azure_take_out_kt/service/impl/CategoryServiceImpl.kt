package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.mapper.CategoryMapper
import moe.scarlet.azure_take_out_kt.pojo.Category
import moe.scarlet.azure_take_out_kt.pojo.QueryResult
import moe.scarlet.azure_take_out_kt.pojo.dto.CategoryDTO
import moe.scarlet.azure_take_out_kt.pojo.dto.CategoryPageQueryDTO
import moe.scarlet.azure_take_out_kt.service.CategoryService
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl : ServiceImpl<CategoryMapper, Category>(), CategoryService {

    override fun pageQuery(categoryPageQueryDTO: CategoryPageQueryDTO): QueryResult<Category> {
        TODO("Not yet implemented")
    }

    override fun status(status: Int, id: Long) {
        TODO("Not yet implemented")
    }

    override fun save(categoryDTO: CategoryDTO) {
        TODO("Not yet implemented")
    }

    override fun update(categoryDTO: CategoryDTO) {
        TODO("Not yet implemented")
    }

}