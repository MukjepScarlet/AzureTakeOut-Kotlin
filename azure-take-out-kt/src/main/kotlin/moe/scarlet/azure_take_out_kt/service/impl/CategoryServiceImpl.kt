package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.exception.ExceptionType
import moe.scarlet.azure_take_out_kt.extension.asQueryResult
import moe.scarlet.azure_take_out_kt.extension.selectList
import moe.scarlet.azure_take_out_kt.extension.selectPage
import moe.scarlet.azure_take_out_kt.extension.update
import moe.scarlet.azure_take_out_kt.mapper.CategoryMapper
import moe.scarlet.azure_take_out_kt.pojo.Category
import moe.scarlet.azure_take_out_kt.pojo.QueryResult
import moe.scarlet.azure_take_out_kt.pojo.dto.CategoryDTO
import moe.scarlet.azure_take_out_kt.pojo.dto.CategoryPageQueryDTO
import moe.scarlet.azure_take_out_kt.service.CategoryService
import moe.scarlet.azure_take_out_kt.service.DishService
import moe.scarlet.azure_take_out_kt.service.SetMealService
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(
    private val categoryMapper: CategoryMapper,
    private val dishService: DishService,
    private val setMealService: SetMealService
) : ServiceImpl<CategoryMapper, Category>(), CategoryService {

    override fun pageQuery(categoryPageQueryDTO: CategoryPageQueryDTO): QueryResult<Category> {
        val (name, page, pageSize, type) = categoryPageQueryDTO
        return categoryMapper.selectPage(page, pageSize) {
            !name.isNullOrEmpty() then Category::name like name
            (type != null) then Category::type eq type
        }.asQueryResult()
    }

    override fun status(status: Int, id: Long) {
        categoryMapper.update {
            Category::id eq id
            Category::status set status
        }
    }

    override fun save(categoryDTO: CategoryDTO) {
        val (id, name, sort, type) = categoryDTO
        categoryMapper.insert(Category(id ?: 0L, type, name, sort))
    }

    override fun update(categoryDTO: CategoryDTO) {
        val (id, name, sort, type) = categoryDTO

        // id不能为空
        val target = id?.let(categoryMapper::selectById) ?: throw ExceptionType.ID_NOT_FOUND.asException()

        categoryMapper.updateById(target.copy(name = name, sort = sort, type = type))
    }

    override fun removeById(id: Long) {
        // 确保没有引用要删除的行 (作为外键)
        if (dishService.countByCategoryId(id) > 0)
            throw ExceptionType.CATEGORY_BE_RELATED_BY_DISH.asException()

        if (setMealService.countByCategoryId(id) > 0)
            throw ExceptionType.CATEGORY_BE_RELATED_BY_SETMEAL.asException()

        categoryMapper.deleteById(id)
    }

    override fun list(type: Int?): List<Category> =
        categoryMapper.selectList {
            (type != null) then Category::type eq type
        }

}