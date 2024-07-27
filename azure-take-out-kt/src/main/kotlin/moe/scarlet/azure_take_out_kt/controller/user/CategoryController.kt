package moe.scarlet.azure_take_out_kt.controller.user

import io.swagger.v3.oas.annotations.Operation
import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.service.CategoryService
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("userCategoryController")
@RequestMapping("/user/category")
class CategoryController(
    private val categoryService: CategoryService
) {

    @Operation(summary = "查询指定类型分类")
    @GetMapping("/list")
    @Cacheable(value = ["category"])
    fun list(type: Int?) = JsonResult.success(categoryService.list(type))

}