package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.constant.StatusConstant
import moe.scarlet.azure_take_out_kt.exception.ExceptionType
import moe.scarlet.azure_take_out_kt.mapper.CategoryMapper
import moe.scarlet.azure_take_out_kt.mapper.SetMealMapper
import moe.scarlet.azure_take_out_kt.pojo.QueryResult
import moe.scarlet.azure_take_out_kt.pojo.SetMeal
import moe.scarlet.azure_take_out_kt.pojo.SetMealDish
import moe.scarlet.azure_take_out_kt.pojo.dto.SetMealDTO
import moe.scarlet.azure_take_out_kt.pojo.dto.SetMealPageQueryDTO
import moe.scarlet.azure_take_out_kt.pojo.vo.SetMealVO
import moe.scarlet.azure_take_out_kt.pojo.vo.SetMealWithDishesVO
import moe.scarlet.azure_take_out_kt.service.SetMealDishService
import moe.scarlet.azure_take_out_kt.service.SetMealService
import org.springframework.stereotype.Service

@Service
class SetMealServiceImpl(
    private val setMealMapper: SetMealMapper,
    private val setMealDishService: SetMealDishService,
    private val categoryMapper: CategoryMapper
) : ServiceImpl<SetMealMapper, SetMeal>(), SetMealService {

    override fun countByCategoryId(categoryId: Long) =
        this.count(KtQueryWrapper(SetMeal::class.java).eq(SetMeal::categoryId, categoryId))

    override fun getByCategoryId(categoryId: Long): List<SetMeal> =
        this.list(KtQueryWrapper(SetMeal::class.java).eq(SetMeal::categoryId, categoryId))

    override fun pageQuery(setMealPageQueryDTO: SetMealPageQueryDTO): QueryResult<SetMealVO> {
        val (categoryId, name, page, pageSize, status) = setMealPageQueryDTO
        val result = setMealMapper.selectPage(
            Page(page, pageSize),
            KtQueryWrapper(SetMeal::class.java)
                .eq(categoryId != null, SetMeal::categoryId, categoryId)
                .eq(status != null, SetMeal::status, status)
                .like(!name.isNullOrEmpty(), SetMeal::name, name)
        )
        return QueryResult(
            result.total,
            result.records.map {
                SetMealVO(
                    it.categoryId,
                    categoryMapper.getNameById(it.categoryId),
                    it.description,
                    it.id,
                    it.image,
                    it.name,
                    it.price,
                    it.status,
                    it.updateTime!!
                )
            }
        )
    }

    override fun status(status: Int, id: Long) {
        this.update(KtUpdateWrapper(SetMeal::class.java).eq(SetMeal::id, id).set(SetMeal::status, status))
    }

    override fun save(setMealDTO: SetMealDTO) {
        val (categoryId, description, id, image, name, price, setmealDishes, status) = setMealDTO

        val newRow = SetMeal(id ?: 0L, categoryId, name, price, status, description, image)

        this.save(newRow)

        // 插入对应的套餐-菜品关联
        if (!setmealDishes.isNullOrEmpty())
            setMealDishService.saveBatch(newRow.id, setmealDishes)
    }

    override fun update(setMealDTO: SetMealDTO) {
        val (categoryId, description, id, image, name, price, setmealDishes, status) = setMealDTO

        this.updateById(SetMeal(id!!, categoryId, name, price, status, description, image))

        // 删除原先的菜品数据
        setMealDishService.removeBySetMealId(id)

        // 添加新的菜品数据
        if (!setmealDishes.isNullOrEmpty())
            setMealDishService.saveBatch(id, setmealDishes)
    }

    override fun delete(idList: List<Long>) {
        if (idList.isEmpty()) return

        // 判断是否有在售的
        if (idList.any { this.getById(it).status == StatusConstant.ENABLE })
            throw ExceptionType.SETMEAL_ON_SALE.asException()

        // 不用判断是否关联菜品, 因为是套餐依赖于菜品
        // 删除对应的关联
        setMealDishService.remove(KtQueryWrapper(SetMealDish::class.java).`in`(SetMealDish::setmealId, idList))

        this.removeByIds(idList)
    }

    override fun getByIdWithDishes(id: Long): SetMealWithDishesVO {
        val (_, categoryId, name, price, status, description, image, _, updateTime, _, _) = this.getById(id)
        return SetMealWithDishesVO(
            categoryId, categoryMapper.getNameById(categoryId), description, id, image, name, price,
            setMealDishService.getBySetMealId(id), status, updateTime!!
        )
    }

}