package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.extension.delete
import moe.scarlet.azure_take_out_kt.extension.selectList
import moe.scarlet.azure_take_out_kt.mapper.DishFlavorMapper
import moe.scarlet.azure_take_out_kt.pojo.DishFlavor
import moe.scarlet.azure_take_out_kt.pojo.dto.DishFlavorDTO
import moe.scarlet.azure_take_out_kt.service.DishFlavorService
import org.springframework.stereotype.Service

@Service
class DishFlavorServiceImpl(
    private val dishFlavorMapper: DishFlavorMapper,
) : ServiceImpl<DishFlavorMapper, DishFlavor>(), DishFlavorService {

    override fun saveBatch(dishId: Long, dishFlavorDTO: Collection<DishFlavorDTO>) {
        dishFlavorMapper.insert(dishFlavorDTO.map { DishFlavor(it.id ?: 0L, dishId, it.name, it.value) })
    }

    override fun listByDishId(dishId: Long) = dishFlavorMapper.selectList {
        DishFlavor::dishId eq dishId
    }

    override fun removeByDishId(dishId: Long) {
        dishFlavorMapper.delete {
            DishFlavor::dishId eq dishId
        }
    }

}
