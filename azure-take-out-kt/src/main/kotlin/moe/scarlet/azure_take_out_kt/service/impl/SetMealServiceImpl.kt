package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.mapper.SetMealMapper
import moe.scarlet.azure_take_out_kt.pojo.SetMeal
import moe.scarlet.azure_take_out_kt.service.SetMealService
import org.springframework.stereotype.Service

@Service
class SetMealServiceImpl : ServiceImpl<SetMealMapper, SetMeal>(), SetMealService {

    override fun countByCategoryId(categoryId: Long) =
        this.count(KtQueryWrapper(SetMeal::class.java).eq(SetMeal::categoryId, categoryId))

}