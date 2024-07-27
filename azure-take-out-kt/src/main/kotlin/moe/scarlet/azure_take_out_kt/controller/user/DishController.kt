package moe.scarlet.azure_take_out_kt.controller.user

import io.swagger.v3.oas.annotations.Operation
import moe.scarlet.azure_take_out_kt.pojo.JsonResult
import moe.scarlet.azure_take_out_kt.pojo.vo.DishWithFlavorsVO
import moe.scarlet.azure_take_out_kt.service.DishService
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("userDishController")
@RequestMapping("/user/dish")
class DishController(
    private val dishService: DishService
) {

    @Operation(summary = "按分类查询菜品")
    @GetMapping("/list")
    @Cacheable(value = ["dish"])
    fun list(categoryId: Long): JsonResult<List<DishWithFlavorsVO>> =
        JsonResult.success(dishService.listWithFlavors(categoryId))

}