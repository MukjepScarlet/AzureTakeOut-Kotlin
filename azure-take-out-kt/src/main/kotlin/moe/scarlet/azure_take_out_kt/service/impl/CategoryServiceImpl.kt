package moe.scarlet.azure_take_out_kt.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import moe.scarlet.azure_take_out_kt.mapper.CategoryMapper
import moe.scarlet.azure_take_out_kt.pojo.Category
import moe.scarlet.azure_take_out_kt.service.CategoryService
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl : ServiceImpl<CategoryMapper, Category>(), CategoryService {
    
}