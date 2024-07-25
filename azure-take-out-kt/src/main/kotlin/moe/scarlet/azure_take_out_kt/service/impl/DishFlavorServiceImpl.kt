package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.mapper.DishFlavorMapper
import moe.scarlet.azure_take_out_kt.pojo.DishFlavor
import moe.scarlet.azure_take_out_kt.pojo.dto.DishFlavorDTO
import moe.scarlet.azure_take_out_kt.service.DishFlavorService
import org.springframework.stereotype.Service


@Service
class DishFlavorServiceImpl : ServiceImpl<DishFlavorMapper, DishFlavor>(), DishFlavorService {

    override fun saveBatch(dishId: Long, dishFlavorDTO: Collection<DishFlavorDTO>) {
        super<ServiceImpl>.saveBatch(dishFlavorDTO.map { DishFlavor(it.id ?: 0L, dishId, it.name, it.value) })
    }

    override fun getByDishId(dishId: Long): List<DishFlavor> = this.list(KtQueryWrapper(DishFlavor::class.java).eq(DishFlavor::dishId, dishId))

    override fun removeByDishId(dishId: Long) {
        this.remove(KtQueryWrapper(DishFlavor::class.java).eq(DishFlavor::dishId, dishId))
    }

}
