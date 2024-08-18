package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.extension.delete
import moe.scarlet.azure_take_out_kt.extension.selectCount
import moe.scarlet.azure_take_out_kt.extension.selectList
import moe.scarlet.azure_take_out_kt.mapper.SetMealDishMapper
import moe.scarlet.azure_take_out_kt.pojo.SetMealDish
import moe.scarlet.azure_take_out_kt.pojo.dto.SetMealDishDTO
import moe.scarlet.azure_take_out_kt.service.SetMealDishService
import org.springframework.stereotype.Service

@Service
class SetMealDishServiceImpl(
    private val setMealDishMapper: SetMealDishMapper,
) : ServiceImpl<SetMealDishMapper, SetMealDish>(), SetMealDishService {

    override fun countByDishId(dishId: Long) = setMealDishMapper.selectCount {
        SetMealDish::dishId eq dishId
    }

    override fun countBySetMealId(setmealId: Long) = setMealDishMapper.selectCount {
        SetMealDish::setmealId eq setmealId
    }

    override fun getBySetMealId(setmealId: Long) = setMealDishMapper.selectList {
        SetMealDish::setmealId eq setmealId
    }

    override fun saveBatch(setmealId: Long, setMealDTO: Collection<SetMealDishDTO>) {
        setMealDishMapper.insert(setMealDTO.map {
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
        setMealDishMapper.delete {
            SetMealDish::setmealId eq setmealId
        }
    }

}