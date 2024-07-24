package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.mapper.DishMapper
import moe.scarlet.azure_take_out_kt.pojo.Dish
import moe.scarlet.azure_take_out_kt.service.DishService
import org.springframework.stereotype.Service

@Service
class DishServiceImpl : ServiceImpl<DishMapper, Dish>(), DishService {

    override fun countByCategoryId(categoryId: Long) =
        this.count(KtQueryWrapper(Dish::class.java).eq(Dish::categoryId, categoryId))

}