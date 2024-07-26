package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.mapper.SetMealDishMapper
import moe.scarlet.azure_take_out_kt.pojo.SetMealDish
import moe.scarlet.azure_take_out_kt.pojo.dto.SetMealDishDTO
import moe.scarlet.azure_take_out_kt.service.SetMealDishService
import org.springframework.stereotype.Service

@Service
class SetMealDishServiceImpl : ServiceImpl<SetMealDishMapper, SetMealDish>(), SetMealDishService {

    override fun countByDishId(dishId: Long) =
        this.count(KtQueryWrapper(SetMealDish::class.java).eq(SetMealDish::dishId, dishId))

    override fun countBySetMealId(setmealId: Long) =
        this.count(KtQueryWrapper(SetMealDish::class.java).eq(SetMealDish::setmealId, setmealId))

    override fun getBySetMealId(setmealId: Long): List<SetMealDish> =
        this.list(KtQueryWrapper(SetMealDish::class.java).eq(SetMealDish::setmealId, setmealId))

    override fun saveBatch(setmealId: Long, setMealDTO: Collection<SetMealDishDTO>) {
        super<ServiceImpl>.saveBatch(setMealDTO.map {
            SetMealDish(
                it.id ?: 0L,
                setmealId,
                it.dishId,
                it.name,
                it.price,
                it.copies
            )
        })
    }

    override fun removeBySetMealId(setmealId: Long) {
        this.remove(KtQueryWrapper(SetMealDish::class.java).eq(SetMealDish::setmealId, setmealId))
    }

}