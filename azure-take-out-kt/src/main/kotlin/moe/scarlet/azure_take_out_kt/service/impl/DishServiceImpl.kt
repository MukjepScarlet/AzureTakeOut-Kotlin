package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.constant.StatusConstant
import moe.scarlet.azure_take_out_kt.exception.ExceptionType
import moe.scarlet.azure_take_out_kt.mapper.CategoryMapper
import moe.scarlet.azure_take_out_kt.mapper.DishMapper
import moe.scarlet.azure_take_out_kt.pojo.Dish
import moe.scarlet.azure_take_out_kt.pojo.DishFlavor
import moe.scarlet.azure_take_out_kt.pojo.QueryResult
import moe.scarlet.azure_take_out_kt.pojo.dto.DishDTO
import moe.scarlet.azure_take_out_kt.pojo.dto.DishPageQueryDTO
import moe.scarlet.azure_take_out_kt.pojo.vo.DishVO
import moe.scarlet.azure_take_out_kt.pojo.vo.DishWithFlavorsVO
import moe.scarlet.azure_take_out_kt.service.DishFlavorService
import moe.scarlet.azure_take_out_kt.service.DishService
import moe.scarlet.azure_take_out_kt.service.SetMealDishService
import org.springframework.stereotype.Service

@Service
class DishServiceImpl(
    private val dishMapper: DishMapper,
    private val dishFlavorService: DishFlavorService,
    private val setMealDishService: SetMealDishService,
    private val categoryMapper: CategoryMapper
) : ServiceImpl<DishMapper, Dish>(), DishService {

    override fun countByCategoryId(categoryId: Long) =
        this.count(KtQueryWrapper(Dish::class.java).eq(Dish::categoryId, categoryId))

    // 非常丑陋, 但是我不想写一堆SQL, 就用子查询吧
    override fun pageQuery(dishPageQueryDTO: DishPageQueryDTO): QueryResult<DishVO> {
        val (categoryId, name, page, pageSize, status) = dishPageQueryDTO
        val result = dishMapper.selectPage(
            Page(page, pageSize),
            KtQueryWrapper(Dish::class.java)
                .eq(categoryId != null, Dish::categoryId, categoryId)
                .eq(status != null, Dish::status, status)
                .like(!name.isNullOrEmpty(), Dish::name, name)
        )
        return QueryResult(
            result.total,
            result.records.map {
                DishVO(
                    it.id,
                    it.name,
                    it.categoryId,
                    it.price,
                    it.image,
                    it.description,
                    it.status,
                    it.updateTime!!,
                    categoryMapper.getNameById(it.categoryId)
                )
            }
        )
    }

    override fun status(status: Int, id: Long) {
        this.update(KtUpdateWrapper(Dish::class.java).eq(Dish::id, id).set(Dish::status, status))
    }

    override fun save(dishDTO: DishDTO) {
        val (id, name, categoryId, price, image, status, description, flavors) = dishDTO

        val newRow = Dish(id ?: 0L, name, categoryId, price, image, description, status)

        this.save(newRow)

        // 插入相应的口味数据 (根据插入后生成的自增ID)
        if (!flavors.isNullOrEmpty())
            dishFlavorService.saveBatch(newRow.id, flavors)
    }

    override fun update(dishDTO: DishDTO) {
        val (id, name, categoryId, price, image, status, description, flavors) = dishDTO

        this.updateById(Dish(id!!, name, categoryId, price, image, description, status))

        // 删除原先的口味数据
        dishFlavorService.removeByDishId(id)

        // 插入修改后的口味数据
        if (!flavors.isNullOrEmpty())
            dishFlavorService.saveBatch(id, flavors)
    }

    override fun delete(idList: List<Long>) {
        if (idList.isEmpty()) return

        // 判断是否有在售的
        if (idList.any { this.getById(it).status == StatusConstant.ENABLE })
            throw ExceptionType.DISH_ON_SALE.asException()

        // 判断是否与套餐关联
        if (idList.any { setMealDishService.countByDishId(it) > 0 })
            throw ExceptionType.DISH_BE_RELATED_BY_SETMEAL.asException()

        // 删除对应的口味
        dishFlavorService.remove(KtQueryWrapper(DishFlavor::class.java).`in`(DishFlavor::dishId, idList))

        this.removeByIds(idList)
    }

    override fun getByIdWithFlavor(id: Long): DishWithFlavorsVO {
        val (_, name, categoryId, price, image, description, status, _, updateTime) = this.getById(id)
        return DishWithFlavorsVO(
            categoryId, categoryMapper.getNameById(categoryId), description, dishFlavorService.getByDishId(id),
            id, image, name, price, status, updateTime!!
        )
    }

    override fun list(categoryId: Long): List<Dish> =
        this.list(KtQueryWrapper(Dish::class.java).eq(Dish::categoryId, categoryId))

}
